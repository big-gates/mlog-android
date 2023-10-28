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
    private val mockMovieEntities = listOf(
        MovieEntity(
            id = 13,
            adult = false,
            backdropPath = "/qdIMHd4sEfJSckfVJfKQvisL02a.jpg",
            originalTitle = "Forrest Gump",
            posterPath = "/xdJxoq0dtkchOkUz5UVKuxn7a2V.jpg",
            title = "포레스트 검프",
            voteAverage = 8.5
        ),
        MovieEntity(
            id = 98,
            adult = false,
            backdropPath = "/3ZVEtQxVPpEp5LNpAULDcxadTU3.jpg",
            originalTitle = "Gladiator",
            posterPath = "/yemF0xxGU56Pf3JXxVr4C6kuKng.jpg",
            title = "글래디에이터",
            voteAverage = 8.2
        ),
        MovieEntity(
            id = 105,
            adult = false,
            backdropPath = "/3lbTiIN8cVonMUQwaeh5nvn61lr.jpg",
            originalTitle = "Back to the Future",
            posterPath = "/7gGicAJdHRzaUd6qLjH5bSW4Cgi.jpg",
            title = "빽 투 더 퓨쳐",
            voteAverage = 8.3
        )
    )

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

    @Test
    fun if_you_have_a_movie_with_the_same_id_in_the_database_it_should_be_updated_and_if_have_new_content_it_should_be_stored() = runTest{
        //given
        val data = mockMovieEntities
        movieDao.upsertMovies(data)
        val updateData = mockMovieEntities.map { it.copy(title = "${it.title}_updated") }.toMutableList()
        updateData.add(
            MovieEntity(
                id = 118,
                adult = false,
                backdropPath = "/atoIgfAk2Ig2HFJLD0VUnjiPWEz.jpg",
                originalTitle = "Charlie and the Chocolate Factory",
                posterPath = "/b3422i6tIf91RttBRfIz70dBSwn.jpg",
                title = "찰리와 초콜릿 공장",
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