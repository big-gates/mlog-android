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
import com.kychan.mlog.core.model.MyRatedMovies
import com.kychan.mlog.core.model.MyWantToWatchMovie
import com.kychan.mlog.core.model.Rated
import com.kychan.mlog.core.model.WantToWatch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
        myWantToWatchDao = FakeMyWantToWatchDao(myMovieDao, myGenresDao)
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

    @Test
    fun `좋아요 영화 인덱스 0번째인 mock 데이터 삽입 후 dao와 repo에서 가져오는 데이터가 일치해야 한다'`() = runTest {
        repository.insertMyWantMovie(
            myMovie = myMovieList[0],
            wantToWatch = wantToWatchList[0],
            myGenres = myGenreList,
        )

        val expected = myWantToWatchDao.getMyWantToWatchMovies().first().map(MyWantToWatchMovieVO::toDomain)
        val result = repository.getMyWantToWatchMovies().first()

        assertEquals(expected, result)
    }

    @Test
    fun `평가한 영화 인덱스 0번째인 mock 데이터 삽입 후 dao와 repo에서 가져오는 데이터가 일치해야 한다`() = testScope.runTest {
        repository.updateMyRatedMovie(
            myMovie = myMovieList[0],
            rated = ratedList[0],
            myGenres = myGenreList,
        )

        val expected = myRatedDao.getMyRatedMovies().first().map(MyRatedMoviesVO::toDomain).toString()
        val result = repository.getMyRatedMovies().first().toString()

        assertEquals(expected, result)
    }

    @Test
    fun `좋아요 영화 추가 확인 후 삭제`() = testScope.runTest {
        repository.insertMyWantMovie(
            myMovie = myMovieList[0],
            wantToWatch = wantToWatchList[0],
            myGenres = myGenreList,
        )

        val result = repository.getMyWantToWatchMovies().first()
        assertNotNull(result)

        repository.deleteMyWantMovie(
            myMovie = myMovieList[0],
            wantToWatch = wantToWatchList[0],
        )

        val expected = emptyList<MyWantToWatchMovie>()
        val result2 = repository.getMyWantToWatchMovies().first()

        assertEquals(expected, result2)
    }

    @Test
    fun `평가한 영화 추가 확인 후 삭제`() = testScope.runTest {
        repository.updateMyRatedMovie(
            myMovie = myMovieList[0],
            rated = ratedList[0],
            myGenres = myGenreList,
        )

        val result = repository.getMyRatedMovies().first()
        assertNotNull(result)

        repository.deleteMyRatedMovie(
            myMovie = myMovieList[0],
            rated = ratedList[0],
        )

        val expected = emptyList<MyRatedMovies>()
        val result2 = repository.getMyRatedMovies().first()

        assertEquals(expected, result2)
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