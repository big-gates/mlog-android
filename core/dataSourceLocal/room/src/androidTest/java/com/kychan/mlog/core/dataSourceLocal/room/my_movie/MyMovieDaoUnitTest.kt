package com.kychan.mlog.core.dataSourceLocal.room.my_movie

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kychan.mlog.core.dataSourceLocal.room.MlogDatabase
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyRatedDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyWantToWatchDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieRatedAndWantedVO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MyMovieDaoUnitTest {
    private lateinit var myMovieDao: MyMovieDao
    private lateinit var myRatedDao: MyRatedDao
    private lateinit var myWantToWatchDao: MyWantToWatchDao
    private lateinit var db: MlogDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MlogDatabase::class.java).build()
        myMovieDao = db.myMovieDao()
        myRatedDao = db.myRatedDao()
        myWantToWatchDao = db.myWantToWatchDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun if_the_my_movie_id_is_the_same_it_must_be_modified_with_the_changed_data() = runTest {
        //given
        val data = myMovieList
        val updateData = MyMovieEntity(
            id = 961268,
            adult = true,
            backdropPath = "",
            originalTitle = "a",
            posterPath = "a",
            title = "aa",
            voteAverage = 0.0,
            rank = 1,
        )

        //when
        myMovieDao.upsertMyMovie(updateData)
        val result = myMovieDao.getMyMovies().first()

        //then
        val expected =
            data.map { if (it.id == updateData.id) it.copy(title = updateData.title) else it }
        assert(expected.containsAll(result))
    }

    @Test
    fun save_new_data_with_movie_id_123456() = runTest {
        //given
        val data = myMovieList
        val insertData = MyMovieEntity(
            id = 123456,
            adult = false,
            backdropPath = "",
            originalTitle = "123456",
            posterPath = "123456",
            title = "123456",
            voteAverage = 1.0,
            rank = 4,
        )

        //when
        myMovieDao.upsertMyMovie(insertData)
        val result = myMovieDao.getMyMovies().first()

        //then
        val expected = data + insertData
        assert(expected.containsAll(result))
    }

    @Test
    fun delete_movie_with_id_961269() = runTest {

        //given
        val data = myMovieList
        val deleteData = MyMovieEntity(
            id = 961269,
            adult = false,
            backdropPath = "",
            originalTitle = "b",
            posterPath = "b",
            title = "b",
            voteAverage = 1.0,
            rank = 3,
        )

        //when
        data.forEach {
            myMovieDao.upsertMyMovie(it)
        }

        myMovieDao.deleteMyMovie(deleteData)
        val result = myMovieDao.getMyMovies().first()

        //then
        val expected = data - deleteData
        assertEquals(expected, result)
    }


    @Test
    fun get_Rated_and_Want_to_watch_data_for_the_movie_with_id_961268() = runTest {

        //given
        val findMovieId = 961268

        //when
        myMovieList.forEach { myMovieDao.upsertMyMovie(it) }
        myRatedList.forEach { myRatedDao.upsertRatedMovie(it) }
        myWantToWatchList.forEach { myWantToWatchDao.insertWantMovie(it) }

        val result = myMovieDao.getMyMovieRatedAndWanted(findMovieId).first()


        //then
        val findMovieRated = myRatedList.find { it.myMovieId == findMovieId }
        val findMovieWantToWatch = myWantToWatchList.find { it.myMovieId == findMovieId }
        val expected = MyMovieRatedAndWantedVO(
            rated = findMovieRated?.rated,
            comment = findMovieRated?.comment,
            wantToMovieId = findMovieWantToWatch?.id,
        )
        assertEquals(expected, result)
    }
}