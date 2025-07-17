package com.kychan.mlog.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.kychan.mlog.core.data.testdouble.FakeMovieDao
import com.kychan.mlog.core.data.testdouble.FakeSyncLogDao
import com.kychan.mlog.core.data.testdouble.FakeTagDao
import com.kychan.mlog.core.data.testdouble.FakeTmdbDataSource
import com.kychan.mlog.core.data.testdouble.FakeWatchProviderDao
import com.kychan.mlog.core.data.mediator.MLogMovieMediator
import com.kychan.mlog.core.data.mediator.NetflixMovieMediator
import com.kychan.mlog.core.data.repository.HomeRepositoryImpl
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SyncLogDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.TagDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.WatchProviderDao
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MovieRoomDataSourceImpl
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieVO
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogType
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NetflixMovieMediatorUnitTest {
    private val testScope = TestScope(UnconfinedTestDispatcher())
    private lateinit var movieDao: MovieDao
    private lateinit var tagDao: TagDao
    private lateinit var watchProviderDao: WatchProviderDao
    private lateinit var tmdbDataSource: TMDBDataSource
    private lateinit var movieRoomDataSource: MovieRoomDataSource
    private lateinit var syncLogDao: SyncLogDao

    @Before
    fun setup() {
        tmdbDataSource = FakeTmdbDataSource()
        watchProviderDao = FakeWatchProviderDao()
        tagDao = FakeTagDao()
        movieDao = FakeMovieDao(
            watchProviderDao = watchProviderDao,
            tagDao = tagDao
        )
        syncLogDao = FakeSyncLogDao()
        movieRoomDataSource = MovieRoomDataSourceImpl(
            movieDao = movieDao,
            syncLogDao = syncLogDao,
            tagDao = tagDao,
            watchProviderDao = watchProviderDao
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `정상적인 경우 syncLog의 nextKey값이 endPage값 미만이면 MediatorResult Success를 반환해야하며 endOfPaginationReached는 false를 반환해야한다`() {
        testScope.runTest {

            val syncLog = syncLogDao.getSyncLog(SyncLogType.Netflix_Movie)
            syncLogDao.upsertSyncLog(syncLog.copy(nextKey = 2))

            val endPage = 3
            val remoteMediator = NetflixMovieMediator(
                movieRoomDataSource = movieRoomDataSource,
                tmdbDataSource = tmdbDataSource,
                endPage = endPage
            )

            val pagingState = PagingState<Int, MovieVO>(
                listOf(),
                null,
                PagingConfig(10),
                10
            )

            val result = remoteMediator.load(LoadType.APPEND, pagingState)
            assertTrue { result is RemoteMediator.MediatorResult.Success }
            assertFalse { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `정상적인 경우 syncLog의 nextKey값이 endPage값 이상이면 MediatorResult Success를 반환해야하며 endOfPaginationReached는 true를 반환해야한다`() {
        testScope.runTest {

            val syncLog = syncLogDao.getSyncLog(SyncLogType.Netflix_Movie)
            syncLogDao.upsertSyncLog(syncLog.copy(nextKey = 2))

            val endPage = 2
            val remoteMediator = NetflixMovieMediator(
                movieRoomDataSource = movieRoomDataSource,
                tmdbDataSource = tmdbDataSource,
                endPage = endPage
            )

            val pagingState = PagingState<Int, MovieVO>(
                listOf(),
                null,
                PagingConfig(10),
                10
            )

            val result = remoteMediator.load(LoadType.APPEND, pagingState)
            assertTrue { result is RemoteMediator.MediatorResult.Success }
            assertTrue { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `정상적이지 않은 경우 MediatorResult Error를 반환해야 한다`() {
        testScope.runTest {

            val syncLog = syncLogDao.getSyncLog(SyncLogType.Netflix_Movie)
            syncLogDao.upsertSyncLog(syncLog.copy(nextKey = 3))

            val endPage = 2
            val remoteMediator = NetflixMovieMediator(
                movieRoomDataSource = movieRoomDataSource,
                tmdbDataSource = tmdbDataSource,
                endPage = endPage
            )

            val pagingState = PagingState<Int, MovieVO>(
                listOf(),
                null,
                PagingConfig(10),
                10
            )

            val result = remoteMediator.load(LoadType.APPEND, pagingState)
            assertTrue { result is RemoteMediator.MediatorResult.Error }
        }
    }
}