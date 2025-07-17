package com.kychan.mlog.core.dataSourceLocal.room.my_movie

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kychan.mlog.core.common.extenstions.toDateTimeFormat
import com.kychan.mlog.core.dataSourceLocal.room.MlogDatabase
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyWantToWatchDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WantToWatchesEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MyWantToWatchDaoUnitTest {
    private lateinit var myMovieDao: MyMovieDao
    private lateinit var myWantToWatchDao: MyWantToWatchDao
    private lateinit var db: MlogDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MlogDatabase::class.java).build()
        myMovieDao = db.myMovieDao()
        myWantToWatchDao = db.myWantToWatchDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * 외래키 검증 테스트
     *
     * '보고싶어요'를 저장하려면 해당 영화가 존재해야함
     *
     * '보고싶어요' 저장시 해당 영화가 없으면 SQLiteConstraintException 발생
     */
    @Test
    fun to_save_a_want_to_watch_the_movie_must_exist() = runTest {

        //given
        val insertData = WantToWatchesEntity(
            id = 961268,
            myMovieId = 961268,
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
        )

        //when
        val exception = runCatching {
            myWantToWatchDao.insertWantMovie(insertData)
        }.exceptionOrNull()

        //then
        assertTrue(exception is SQLiteConstraintException)
    }

    /**
     * ForeignKey.CASCADE 검증 테스트
     *
     * 영화가 삭제된다면 관련 영화의 '보고싶어요' 데이터는 삭제되야한다
     */
    @Test
    fun if_a_movie_is_deleted_the_want_to_watch_data_of_the_related_movie_must_be_deleted() = runTest {

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
        myMovieList.forEach { myMovieDao.upsertMyMovie(it) }
        myWantToWatchList.forEach { myWantToWatchDao.insertWantMovie(it) }

        myMovieDao.deleteMyMovie(deleteData)
        val result = myWantToWatchDao.getMyWantToWatchMovies().first()

        //then
        val expected = myWantToWatchList.filter { it.myMovieId != deleteData.id }
        assertEquals(expected, result)
    }

    /**
     * id가 961269인 새로운 데이터를 저장할 수 있어야 한다.
     */
    @Test
    fun testMyWantToWatchMovieInsert() = runTest {

        //given
        val insertData = WantToWatchesEntity(
            id = 961269,
            myMovieId = 961269,
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
        )

        //when
        myMovieList.forEach {
            myMovieDao.upsertMyMovie(it)
        }
        myWantToWatchDao.insertWantMovie(insertData)
        val result = myWantToWatchDao.existToMyWantMovie(insertData.id)

        //then
        val expected = insertData
        assertEquals(expected, result)
    }

    /**
     * id 961269 데이터 삭제
     */
    @Test
    fun testMyWantToWatchDelete() = runTest {

        //given
        val data = myWantToWatchList
        val deleteData = WantToWatchesEntity(
            id = 961269,
            myMovieId = 961269,
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
        )

        //when
        myMovieList.forEach {
            myMovieDao.upsertMyMovie(it)
        }
        myWantToWatchList.forEach {
            myWantToWatchDao.insertWantMovie(it)
        }

        myWantToWatchDao.deleteWantMovie(deleteData)
        val result = myWantToWatchDao.getMyWantToWatchMovies().first().map { it.wantToWatch }

        //then
        val expected = data - deleteData
        assertEquals(expected, result)
    }
}