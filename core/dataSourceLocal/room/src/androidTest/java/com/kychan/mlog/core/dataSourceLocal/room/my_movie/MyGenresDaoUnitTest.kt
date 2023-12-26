package com.kychan.mlog.core.dataSourceLocal.room.my_movie

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kychan.mlog.core.dataSourceLocal.room.MlogDatabase
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyGenresDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MyGenresEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MyGenresDaoUnitTest {
    private lateinit var myMovieDao: MyMovieDao
    private lateinit var myGenresDao: MyGenresDao
    private lateinit var db: MlogDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MlogDatabase::class.java).build()
        myMovieDao = db.myMovieDao()
        myGenresDao = db.myGenresDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * 외래키 검증 테스트
     *
     * 장르를 저장하려면 해당 영화가 존재해야함
     *
     * 장르 저장시 해당 영화가 없으면 SQLiteConstraintException 발생
     */
    @Test
    fun to_save_a_genre_the_movie_must_exist() = runTest {

        //given
        val insertData = listOf(
            MyGenresEntity(
                genreId = 99,
                movieId = 1234566,
            ),
        )

        //when
        val exception = runCatching {
            myGenresDao.upsertTags(insertData)
        }.exceptionOrNull()

        //then
        assertTrue(exception is SQLiteConstraintException)
    }

    /**
     * ForeignKey.CASCADE 검증 테스트
     *
     * 영화가 삭제된다면 관련 영화의 장르 데이터는 삭제되야한다
     */
    @Test
    fun if_a_movie_is_deleted_the_genre_data_of_the_related_movie_must_be_deleted() = runTest {

        //given
        val deleteData =
            MyMovieEntity(
                id = 961268,
                adult = true,
                backdropPath = "",
                originalTitle = "a",
                posterPath = "a",
                title = "a",
                voteAverage = 0.0,
                rank = 1,
            )

        //when
        myMovieDao.upsertMyMovie(myMovieList[0])
        myMovieDao.upsertMyMovie(myMovieList[1])
        myGenresDao.upsertTags(myGenreList)

        myMovieDao.deleteMyMovie(deleteData)
        val result = myGenresDao.getMyGenres().first()

        //then
        val expected = myGenreList.filter { it.movieId != deleteData.id }
        assertEquals(expected, result)
    }

    /**
     * 복합키 검증 테스트
     *
     * 동일한 movie_id, 서로 다른 genre_id 및
     * 동일한 genre_id, 서로 다른 movie_id를 저장할 수 있어야 한다
     */
    @Test
    fun if_the_value_of_one_field_is_the_same_it_must_be_possible_to_save_it_even_if_the_value_of_the_other_field_is_diff() = runTest {

        //given
        val insertData = listOf(
            MyGenresEntity(
                genreId = 81,
                movieId = 961268,
            ),
        )

        //when
        myMovieDao.upsertMyMovie(myMovieList[0])
        myMovieDao.upsertMyMovie(myMovieList[1])
        myGenresDao.upsertTags(myGenreList)

        myGenresDao.upsertTags(insertData)
        val result = myGenresDao.getMyGenres().first()

        //then
        val expected = myGenreList + insertData
        assertEquals(expected, result)
    }
}