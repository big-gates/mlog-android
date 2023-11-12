package com.kychan.mlog.core.dataSourceLocal.room.my_movie

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kychan.mlog.core.dataSourceLocal.room.MlogDatabase
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyRatedDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.RatedEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MyRatedDaoUnitTest {
    private lateinit var myMovieDao: MyMovieDao
    private lateinit var myRatedDao: MyRatedDao
    private lateinit var db: MlogDatabase

    private val myRatedList = listOf(
        RatedEntity(
            id = 961268,
            myMovieId = 961268,
            rated = 4.0f,
            comment = "",
        ),
        RatedEntity(
            id = 1151534,
            myMovieId = 1151534,
            rated = 3.5f,
            comment = "",
        )
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MlogDatabase::class.java).build()
        myMovieDao = db.myMovieDao()
        myRatedDao = db.myRatedDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * 외래키 검증 테스트
     *
     * 평가를 저장하려면 해당 영화가 존재해야함
     *
     * 평가 저장시 해당 영화가 없으면 SQLiteConstraintException 발생
     */
    @Test
    fun to_save_a_rate_the_movie_must_exist() = runTest {

        //given
        val insertData = RatedEntity(
            id = 961268,
            myMovieId = 961268,
            rated = 4.0f,
            comment = "",
        )

        //when
        val exception = runCatching {
            myRatedDao.upsertRatedMovie(insertData)
        }.exceptionOrNull()

        //then
        assertTrue(exception is SQLiteConstraintException)
    }

    /**
     * ForeignKey.CASCADE 검증 테스트
     *
     * 영화가 삭제된다면 관련 영화의 평가 데이터는 삭제되야한다
     */
    @Test
    fun if_a_movie_is_deleted_the_rate_data_of_the_related_movie_must_be_deleted() = runTest {

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
        myRatedList.forEach { myRatedDao.upsertRatedMovie(it) }

        myMovieDao.deleteMyMovie(deleteData)
        val result = myRatedDao.existToMyRatedMovie(deleteData.id)

        //then
        val expected = null
        assertEquals(expected, result)
    }

    /**
     * ID가 동일한 경우 변경된 데이터로 수정해야 합니다.
     */
    @Test
    fun testMyRatedMovieUpdate() = runTest {

        //given
        val data = myRatedList
        val updateData = RatedEntity(
            id = 961268,
            myMovieId = 961268,
            rated = 4.0f,
            comment = "코멘트를 추가했습니다."
        )

        //when
        myMovieList.forEach {
            myMovieDao.upsertMyMovie(it)
        }
        myRatedDao.upsertRatedMovie(updateData)
        val result = myRatedDao.getMyRatedMovies().first().map { it.rated }

        //then
        val expected =
            data.map { if (it.id == updateData.id) it.copy(comment = updateData.comment) else it }
        assert(expected.containsAll(result))
    }


    /**
     * id가 961269인 새로운 데이터를 저장할 수 있어야 한다.
     */
    @Test
    fun testMyRatedMovieInsert() = runTest {

        //given
        val insertData = RatedEntity(
            id = 961269,
            myMovieId = 961269,
            rated = 4.0f,
            comment = "새로운 평가 데이터 추가"
        )

        //when
        myMovieList.forEach {
            myMovieDao.upsertMyMovie(it)
        }
        myRatedDao.upsertRatedMovie(insertData)
        val result = myRatedDao.existToMyRatedMovie(insertData.id)

        //then
        val expected = insertData
        assertEquals(expected, result)
    }

    /**
     * id 961268 데이터 삭제
     */
    @Test
    fun testMyRatedMovieDelete() = runTest {

        //given
        val data = myRatedList
        val deleteData = RatedEntity(
            id = 961268,
            myMovieId = 961268,
            rated = 4.0f,
            comment = ""
        )

        //when
        myMovieList.forEach {
            myMovieDao.upsertMyMovie(it)
        }
        myRatedList.forEach {
            myRatedDao.upsertRatedMovie(it)
        }
        myRatedDao.deleteRatedMovie(deleteData)
        val result = myRatedDao.getMyRatedMovies().first().map { it.rated }

        //then
        val expected = data - deleteData
        assertEquals(expected, result)
    }
}