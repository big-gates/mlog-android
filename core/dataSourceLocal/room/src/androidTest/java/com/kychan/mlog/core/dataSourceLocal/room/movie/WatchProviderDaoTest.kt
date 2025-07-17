package com.kychan.mlog.core.dataSourceLocal.room.movie

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kychan.mlog.core.dataSourceLocal.room.MlogDatabase
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.TagDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.WatchProviderDao
import com.kychan.mlog.core.dataSourceLocal.room.model.GenresEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WatchProviderEntity
import com.kychan.mlog.core.model.WatchProvider
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class WatchProviderDaoTest {

    private lateinit var watchProviderDao: WatchProviderDao
    private lateinit var movieDao: MovieDao
    private lateinit var db: MlogDatabase

    @Before
    fun createDb() = runTest{
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MlogDatabase::class.java,
        ).build()
        watchProviderDao = db.watchProviderDao()
        movieDao = db.movieDao()
        movieDao.upsertMovies(mockMovieEntities)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * 외래키 검증 테스트
     *
     * WatchProvider를 저장하기 위해서는 같은 movie id를 갖고 있는 Moive를 먼저 저장해야한다.
     *
     * movie id가 없는 경우 SQLiteConstraintException을 발생시킨다.
     */
    @Test
    fun movie_must_be_saved_first_before_saving_watch_provider() = runTest{
        //given
        val data = listOf(
            WatchProviderEntity(
                watchProviderId = WatchProvider.MLOG_ID,
                movieId = 123123123,
                rank = 1
            )
        )

        //when
        val exception = runCatching { watchProviderDao.upsertWatchProviders(data) }
            .exceptionOrNull()

        //then
        assertTrue(exception is SQLiteConstraintException)
    }

    /**
     * 외래키 ForeignKey.CASCADE 검증 테스트
     *
     * movie entity가 삭제되면 watch provider entity도 삭제되어야한다.
     */
    @Test
    fun if_the_movie_entity_is_deleted_the_watch_provider_entity_should_be_deleted_as_well() = runTest {
        //given
        val data = mockWatchProviderEntities
        watchProviderDao.upsertWatchProviders(data)
        val watchProvider = watchProviderDao.getMovieIds(WatchProvider.NETFLIX_ID).first()
        assertTrue(watchProvider.isNotEmpty())

        //when
        movieDao.deleteMovies(mockMovieEntities.map { it.id })

        //then
        val result = watchProviderDao.getMovieIds(WatchProvider.NETFLIX_ID).first()
        assertTrue(result.isEmpty())
    }

    /**
     * unique constraint 검증 테스트
     *
     * 데이터베이스에 다른 id를 가지면서 동일한 movieId, watch_provider_id가 존재 하면
     *
     * 데이터베이스에 저장 돼서는 안된다.
     *
     * SQLiteConstraintException을 발생시켜야 한다.
     */
    @Test
    fun if_the_id_is_not_same_and_the_movieId_is_same_and_the_watch_provider_id_is_same_it_is_must_be_not_stored_in_the_database() = runTest {
        //given
        val data = listOf(
            WatchProviderEntity(
                watchProviderId = WatchProvider.MLOG_ID,
                movieId = 13,
                rank = 1
            ),
        )
        watchProviderDao.upsertWatchProviders(data)

        //when
        val exception = runCatching {
            watchProviderDao.upsertWatchProviders(data)
        }.exceptionOrNull()


        //then
        assertTrue(exception is SQLiteConstraintException)
    }

    /**
     * update 테스트
     *
     * 데이터베이스에 동일한 id, movieId, watch_provider_id가 존재 하면 update를 해야한다.
     */
    @Test
    fun if_the_id_is_same_and_the_movieId_is_same_and_the_watch_provider_id_is_same_it_is_must_be_updated_in_the_database() = runTest {
        //given
        val data = listOf(
            WatchProviderEntity(
                id = 1,
                watchProviderId = WatchProvider.MLOG_ID,
                movieId = 13,
                rank = 1
            ),
        )
        watchProviderDao.upsertWatchProviders(data)
        val updatedData = data.map { it.copy(rank = it.rank+1) }

        //when
        watchProviderDao.upsertWatchProviders(updatedData)

        //then
        val result = watchProviderDao.getWatchProviders().first()
        assertTrue(result.contains(updatedData.first()))
    }
}