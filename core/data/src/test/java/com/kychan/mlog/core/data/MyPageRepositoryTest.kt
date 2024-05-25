package com.kychan.mlog.core.data

import com.kychan.mlog.core.common.extenstions.toDateTimeFormat
import com.kychan.mlog.core.data.repository.MyPageRepository
import com.kychan.mlog.core.data.repository.MyPageRepositoryImpl
import com.kychan.mlog.core.data.testdoubles.FakeMyGenresDao
import com.kychan.mlog.core.data.testdoubles.FakeMyMovieDao
import com.kychan.mlog.core.data.testdoubles.FakeMyRatedDao
import com.kychan.mlog.core.data.testdoubles.FakeMyWantToWatchDao
import com.kychan.mlog.core.dataSourceLocal.room.datasource.fake.FakeMyMovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import com.kychan.mlog.core.dataSourceLocal.room.model.MyWantToWatchMovieVO
import com.kychan.mlog.core.dataSourceLocal.room.model.toDomain
import com.kychan.mlog.core.model.MyMovie
import com.kychan.mlog.core.model.Rated
import com.kychan.mlog.core.model.WantToWatch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MyPageRepositoryTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var myMovieDao : FakeMyMovieDao
    private lateinit var myRatedDao : FakeMyRatedDao
    private lateinit var myWantToWatchDao : FakeMyWantToWatchDao
    private lateinit var myGenresDao : FakeMyGenresDao
    private lateinit var myMovieRoomDataSource : FakeMyMovieRoomDataSource

    private lateinit var repository: MyPageRepository

    @Before
    fun setup() {
        myMovieDao = FakeMyMovieDao()
        myGenresDao = FakeMyGenresDao()
        myRatedDao = FakeMyRatedDao(myMovieDao, myGenresDao)
        myWantToWatchDao = FakeMyWantToWatchDao()
        myMovieRoomDataSource = FakeMyMovieRoomDataSource(
            myMovieDao = myMovieDao,
            myRatedDao = myRatedDao,
            myWantToWatchDao = myWantToWatchDao,
            myGenresDao = myGenresDao,
        )
        repository = MyPageRepositoryImpl(
            myMovieRoomDataSource = myMovieRoomDataSource
        )
    }
    /*
        getMyRatedMovies1
        getMyWantToWatchMovies1
        updateMyRatedMovie
        insertMyWantMovie
        deleteMyWantMovie
        deleteMyRatedMovie
        existToMyRatedMovie
        existToMyWantMovie
     */

    @Test
    fun getMyWantToWatchMovies() = runTest {

        assertEquals(
            myWantToWatchDao.getMyWantToWatchMovies().first()
                .map(MyWantToWatchMovieVO::toDomain),
            repository.getMyWantToWatchMovies().first()
        )
    }

    @Test
    fun updateMyRatedMovie() = testScope.runTest {
        repository.updateMyRatedMovie(
            myMovie = myMovieList[0],
            rated = ratedList[0],
            myGenres = myGenreList,
        )
        val test1 = myRatedDao.getMyRatedMovies().first().map(MyRatedMoviesVO::toDomain).toString()
        val test2 = repository.getMyRatedMovies().first().toString()

        assertEquals(test1, test2)
    }


}

private val myMovieList = listOf(
    MyMovie(
        id = 961268,
        adult = true,
        backdropPath = "",
        originalTitle = "a",
        posterPath = "a",
        title = "a",
        voteAverage = 0.0,
        rank = 1,
    ),
    MyMovie(
        id = 1151534,
        adult = false,
        backdropPath = "",
        originalTitle = "b",
        posterPath = "b",
        title = "b",
        voteAverage = 1.0,
        rank = 2,
    ),
    MyMovie(
        id = 961269,
        adult = false,
        backdropPath = "",
        originalTitle = "b",
        posterPath = "b",
        title = "b",
        voteAverage = 1.0,
        rank = 3,
    ),
)

private val ratedList = listOf(
    Rated(
        id = 961268,
        myMovieId = 961268,
        rated = 4.0f,
        comment = "",
    ),
    Rated(
        id = 1151534,
        myMovieId = 1151534,
        rated = 3.5f,
        comment = "",
    )
)

private val wantToWatchList = listOf(
    WantToWatch(
        id = 961268,
        myMovieId = 961268,
        createAt = System.currentTimeMillis().toDateTimeFormat(),
    ),
    WantToWatch(
        id = 1151534,
        myMovieId = 1151534,
        createAt = System.currentTimeMillis().toDateTimeFormat(),
    ),
)
private val myGenreList = mutableListOf(28,80,53)