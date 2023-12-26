package com.kychan.mlog.core.dataSourceLocal.room.movie

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kychan.mlog.core.dataSourceLocal.room.MlogDatabase
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var movieDao: MovieDao
    private lateinit var db: MlogDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MlogDatabase::class.java,
        ).build()
        movieDao = db.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun movies_must_be_stored_in_the_database() = runTest{
        //given
        val data = mockMovieEntities

        //when
        movieDao.upsertMovies(data)

        //then
        val result = movieDao.getMovies().first()
        assertTrue(result.containsAll(data))
    }

    @Test
    fun if_you_have_a_movie_with_the_same_id_in_the_database_it_should_be_updated() = runTest{
        //given
        val data = mockMovieEntities
        movieDao.upsertMovies(data)
        val updateData = mockMovieEntities.map { it.copy(title = "${it.title}_updated") }

        //when
        movieDao.upsertMovies(updateData)

        //then
        val result = movieDao.getMovies().first()
        assertTrue(result.containsAll(updateData) )
    }

    /**
     * upsert 테스트 (list)
     *
     * list를 전달받았을 경우
     *
     * 데이터베이스에 같은 ID가 있을경우 업데이트를 해야하며 새로운 item인 경우 저장 되어야한다.
     *
     */
    @Test
    fun if_you_have_a_movie_with_the_same_id_in_the_database_it_should_be_updated_and_if_have_new_content_it_should_be_stored() = runTest{
        //given
        val data = mockMovieEntities
        movieDao.upsertMovies(data)
        val updateData = mockMovieEntities.map { it.copy(title = "${it.title}_updated") }.toMutableList()
        updateData.add(
            MovieEntity(
                id = 9999999,
                adult = false,
                backdropPath = "/test",
                originalTitle = "TEST",
                posterPath = "/test",
                title = "TEST",
                voteAverage = 7.0
            )
        )
        //when
        movieDao.upsertMovies(updateData)

        //then
        val result = movieDao.getMovies().first()
        assertTrue(result.containsAll(updateData))
    }

    @Test
    fun movies_must_be_deleted() = runTest {
        //given
        val data = mockMovieEntities
        movieDao.upsertMovies(data)
        val ids = data.map { it.id }

        //when
        movieDao.deleteMovies(ids)

        //then
        val result = movieDao.getMovies().first()
        assertTrue(result.isEmpty())
    }
}