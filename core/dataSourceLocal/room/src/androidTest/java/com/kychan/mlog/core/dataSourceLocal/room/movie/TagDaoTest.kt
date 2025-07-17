package com.kychan.mlog.core.dataSourceLocal.room.movie

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kychan.mlog.core.dataSourceLocal.room.MlogDatabase
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.TagDao
import com.kychan.mlog.core.dataSourceLocal.room.model.GenresEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TagDaoTest {

    private lateinit var tagDao: TagDao
    private lateinit var movieDao: MovieDao
    private lateinit var db: MlogDatabase

    @Before
    fun createDb() = runTest{
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MlogDatabase::class.java,
        ).build()
        tagDao = db.tagDao()
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
     * Tag를 저장하기 위해서는 같은 movie id를 갖고 있는 Moive를 먼저 저장해야한다.
     *
     * movie id가 없는 경우 SQLiteConstraintException을 발생시킨다.
     */
    @Test
    fun movie_must_be_saved_first_before_saving_tag() = runTest{
        //given
        val data = listOf(
            GenresEntity(
                genreId = 12,
                movieId = 123123123
            )
        )

        //when
        val exception = runCatching { tagDao.upsertTags(data) }
            .exceptionOrNull()

        //then
        assertTrue(exception is SQLiteConstraintException)
    }

    /**
     * 외래키 ForeignKey.CASCADE 검증 테스트
     *
     * movie entity가 삭제되면 genre entity도 삭제되어야한다.
     */
    @Test
    fun if_the_movie_entity_is_deleted_the_genre_entity_should_be_deleted_as_well() = runTest {
        //given
        val data = sameMovieIdAndDifferentGenreIdMockTagEntities
        tagDao.upsertTags(data)
        val tags = tagDao.getTags().first()
        assertTrue(tags.isNotEmpty())

        //when
        movieDao.deleteMovies(mockMovieEntities.map { it.id })

        //then
        val result = tagDao.getTags().first()
        assertTrue(result.isEmpty())
    }

    /**
     * 복합키 검증 테스트
     *
     * 동일한 movieId, 서로 다른 genreId인 경우 데이터베이스에 저장 돼야 한다.
     */
    @Test
    fun if_the_same_movieId_and_different_genreId_it_is_must_be_stored_in_the_database() = runTest {
        //given
        val data = sameMovieIdAndDifferentGenreIdMockTagEntities

        //when
        tagDao.upsertTags(data)

        //then
        val result = tagDao.getTags().first()
        assertTrue(result.containsAll(data))
    }
}