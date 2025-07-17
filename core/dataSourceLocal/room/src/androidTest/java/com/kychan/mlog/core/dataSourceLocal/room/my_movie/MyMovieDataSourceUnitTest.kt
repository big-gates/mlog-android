package com.kychan.mlog.core.dataSourceLocal.room.my_movie

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kychan.mlog.core.common.extenstions.toDateTimeFormat
import com.kychan.mlog.core.dataSourceLocal.room.MlogDatabase
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyGenresDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyRatedDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyWantToWatchDao
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSourceImpl
import com.kychan.mlog.core.dataSourceLocal.room.model.MyGenresEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyWantToWatchMovieVO
import com.kychan.mlog.core.dataSourceLocal.room.model.RatedEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WantToWatchesEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MyMovieDataSourceUnitTest{
    private lateinit var myMovieDao: MyMovieDao
    private lateinit var myRatedDao: MyRatedDao
    private lateinit var myWantToWatchDao: MyWantToWatchDao
    private lateinit var myGenresDao: MyGenresDao
    private lateinit var db: MlogDatabase

    private lateinit var myMovieRoomDataSource: MyMovieRoomDataSource


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MlogDatabase::class.java).build()
        myMovieDao = db.myMovieDao()
        myWantToWatchDao = db.myWantToWatchDao()
        myRatedDao = db.myRatedDao()
        myGenresDao = db.myGenresDao()
        myMovieRoomDataSource = MyMovieRoomDataSourceImpl(myMovieDao, myRatedDao, myWantToWatchDao, myGenresDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * id 961268 데이터 삭제
     *
     * '보고싶어요' 영화는 아님
     *
     * 보고싶어요 데이터가 아닌 평가한 영화를 삭제할 때, my_movie도 삭제
     */
    @Test
    fun deleteMyRatedMovie() = runTest {

        //given
        val myMovieEntity = MyMovieEntity(
            id = 961268,
            adult = true,
            backdropPath = "",
            originalTitle = "a",
            posterPath = "a",
            title = "a",
            voteAverage = 0.0,
            rank = 1,
        )
        val deleteData = RatedEntity(
            id = 961268,
            myMovieId = 961268,
            rated = 4.0f,
            comment = ""
        )

        //when
        myMovieList.forEach { myMovieDao.upsertMyMovie(it) }
        myRatedList.forEach { myRatedDao.upsertRatedMovie(it) }
        myGenresDao.upsertTags(myGenreList)

        myMovieRoomDataSource.deleteMyRatedMovie(myMovieEntity, deleteData) // params
        val result1 = myMovieRoomDataSource.getMyRatedMovies().first().find { it.rated.id == deleteData.id }
        val result2 = myMovieDao.getMyMovies().first().find { it.id == myMovieEntity.id }

        //then
        val expected1 = null
        val expected2 = null
        assertEquals(expected1, result1)
        assertEquals(expected2, result2)
    }

    /**
     * id 961268 데이터 삭제
     *
     * '보고싶어요' 영화임
     *
     * 보고싶어요 데이터인 평가한 영화를 삭제할 때, my_movie는 잔재
     */
    @Test
    fun deleteMyRatedMovie2() = runTest {

        //given
        val myMovieEntity = MyMovieEntity(
            id = 961268,
            adult = true,
            backdropPath = "",
            originalTitle = "a",
            posterPath = "a",
            title = "a",
            voteAverage = 0.0,
            rank = 1,
        )
        val deleteData = RatedEntity(
            id = 961268,
            myMovieId = 961268,
            rated = 4.0f,
            comment = ""
        )

        //when
        myMovieList.forEach { myMovieDao.upsertMyMovie(it) }
        myRatedList.forEach { myRatedDao.upsertRatedMovie(it) }
        myWantToWatchList.forEach { myWantToWatchDao.insertWantMovie(it) }
        myGenresDao.upsertTags(myGenreList)

        myMovieRoomDataSource.deleteMyRatedMovie(myMovieEntity, deleteData) // params

        val result1 = myMovieRoomDataSource.getMyRatedMovies().first().find { it.rated.id == deleteData.id }
        val result2 = myMovieDao.getMyMovies().first().find { it.id == myMovieEntity.id }

        //then
        val expected1 = null
        val expected2 = myMovieEntity
        assertEquals(expected1, result1)
        assertEquals(expected2, result2)
    }

    /**
     * id 961268 데이터 삭제
     *
     * '평가한' 영화는 아님
     *
     * 평가한 데이터가 아닌 보고싶어요 영화를 삭제할 때, my_movie도 삭제
     */
    @Test
    fun deleteMyWantMovie() = runTest {

        //given
        val myMovieEntity = MyMovieEntity(
            id = 961268,
            adult = true,
            backdropPath = "",
            originalTitle = "a",
            posterPath = "a",
            title = "a",
            voteAverage = 0.0,
            rank = 1,
        )
        val deleteData = WantToWatchesEntity(
            id = 961268,
            myMovieId = 961268,
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
        )

        //when
        myMovieList.forEach { myMovieDao.upsertMyMovie(it) }
        myWantToWatchList.forEach { myWantToWatchDao.insertWantMovie(it) }
        myGenresDao.upsertTags(myGenreList)

        myMovieRoomDataSource.deleteMyWantMovie(myMovieEntity, deleteData) // params

        val result1 = myMovieRoomDataSource.getMyWantToWatchMovies().first().find { it.wantToWatch.id == deleteData.id }
        val result2 = myMovieDao.getMyMovies().first().find { it.id == myMovieEntity.id }

        //then
        val expected1 = null
        val expected2 = null
        assertEquals(expected1, result1)
        assertEquals(expected2, result2)
    }

    /**
     * id 961268 데이터 삭제
     *
     * '평가한' 영화임
     *
     * 평가한 데이터인 보고싶어요 영화를 삭제할 때, my_movie는 잔재
     */
    @Test
    fun deleteMyWantMovie2() = runTest {

        //given
        val myMovieEntity = MyMovieEntity(
            id = 961268,
            adult = true,
            backdropPath = "",
            originalTitle = "a",
            posterPath = "a",
            title = "a",
            voteAverage = 0.0,
            rank = 1,
        )
        val deleteData = WantToWatchesEntity(
            id = 961268,
            myMovieId = 961268,
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
        )

        //when
        myMovieList.forEach { myMovieDao.upsertMyMovie(it) }
        myRatedList.forEach { myRatedDao.upsertRatedMovie(it) }
        myWantToWatchList.forEach { myWantToWatchDao.insertWantMovie(it) }
        myGenresDao.upsertTags(myGenreList)

        myMovieRoomDataSource.deleteMyWantMovie(myMovieEntity, deleteData) // params

        val result1 = myMovieRoomDataSource.getMyWantToWatchMovies().first().find { it.wantToWatch.id == deleteData.id }
        val result2 = myMovieDao.getMyMovies().first().find { it.id == myMovieEntity.id }

        //then
        val expected1 = null
        val expected2 = myMovieEntity
        assertEquals(expected1, result1)
        assertEquals(expected2, result2)
    }

    /**
     * 주어진 데이터를 db에 저장했을 때 저장한 데이터와 주어진 데이터가 같아야 함
     */
    @Test
    fun insertMyWantToWatchMovie() = runTest {
        //given
        val myMovieEntity = MyMovieEntity(
            id = 961268,
            adult = true,
            backdropPath = "",
            originalTitle = "a",
            posterPath = "a",
            title = "a",
            voteAverage = 0.0,
            rank = 1,
        )
        val insertData = WantToWatchesEntity(
            id = 961268,
            myMovieId = 961268,
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
        )

        //when
        myMovieRoomDataSource.insertMyWantMovie(
            myMovieEntity = myMovieEntity,
            wantToWatchesEntity = insertData,
            myGenres = listOf(1,2)
        )
        val result = myMovieRoomDataSource.getMyWantToWatchMovies().first()[0]

        //then
        val expected = MyWantToWatchMovieVO(
            myMovie = myMovieEntity,
            wantToWatch = insertData,
            tags = listOf(
                MyGenresEntity(
                    genreId = 1,
                    movieId = 961268
                ),
                MyGenresEntity(
                    genreId = 2,
                    movieId = 961268
                )
            )
        )
        assertEquals(result, expected)
    }

    /**
     * rate : 사용자가 0 입력 시 -1로 저장 후 삭제 로직 실행
     *
     * 평점을 -1로 저장 및 수정이 안되어야함.
     */
    @Test
    fun updateMyRatedMovie() = runTest {

        //given
        val myMovieEntity = MyMovieEntity(
            id = 961268,
            adult = true,
            backdropPath = "",
            originalTitle = "a",
            posterPath = "a",
            title = "a",
            voteAverage = 0.0,
            rank = 1,
        )
        val ratedEntity = RatedEntity(
            id = 961268,
            myMovieId = 961268,
            rated = -1f,
            comment = "코멘트 수정 후 평점 0점으로 수정",
        )
        val myGenres = listOf(81)
//        val data = myRatedList
        val insertData = RatedEntity(
            id = 961268,
            myMovieId = 961268,
            rated = 3f,
            comment = "코멘트 남기기",
        )

        //when
        myMovieList.forEach { myMovieDao.upsertMyMovie(it) }
        myRatedDao.upsertRatedMovie(insertData)
        myGenresDao.upsertTags(myGenreList)

        myMovieRoomDataSource.updateMyRatedMovie(myMovieEntity, ratedEntity, myGenres)

        val result = myRatedDao.getMyRatedMovies().first().find { it.rated.myMovieId == ratedEntity.id }

        //then
        val expected = null
        assertEquals(expected, result)
    }
}