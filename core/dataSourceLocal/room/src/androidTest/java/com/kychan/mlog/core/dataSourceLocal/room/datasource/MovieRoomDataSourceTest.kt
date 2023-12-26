package com.kychan.mlog.core.dataSourceLocal.room.datasource

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kychan.mlog.core.common.extenstions.toDateFormat
import com.kychan.mlog.core.dataSourceLocal.room.MlogDatabase
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieVO
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogType
import com.kychan.mlog.core.dataSourceLocal.room.movie.mockGenreEntities
import com.kychan.mlog.core.dataSourceLocal.room.movie.mockMovieEntities
import com.kychan.mlog.core.dataSourceLocal.room.movie.mockSyncLogEntities
import com.kychan.mlog.core.dataSourceLocal.room.movie.mockWatchProviderEntities
import com.kychan.mlog.core.model.WatchProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class MovieRoomDataSourceTest {

    private lateinit var movieRoomDataSourceImpl: MovieRoomDataSourceImpl
    private lateinit var db: MlogDatabase

    @Before
    fun createDb() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MlogDatabase::class.java,
        ).build()
        val movieDao = db.movieDao()
        val tagDao = db.tagDao()
        val syncLogDao = db.syncLogDao()
        val watchProviderDao = db.watchProviderDao()

        movieRoomDataSourceImpl = MovieRoomDataSourceImpl(
            movieDao = movieDao,
            tagDao = tagDao,
            syncLogDao = syncLogDao,
            watchProviderDao = watchProviderDao
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    private suspend fun settingMockData(){
        val movieDao = db.movieDao()
        val tagDao = db.tagDao()
        val syncLogDao = db.syncLogDao()
        val watchProviderDao = db.watchProviderDao()

        movieDao.upsertMovies(mockMovieEntities)
        tagDao.upsertTags(mockGenreEntities)
        mockSyncLogEntities.forEach { syncLogDao.upsertSyncLog(it) }
        watchProviderDao.upsertWatchProviders(mockWatchProviderEntities)
    }

    /**
     * Mlog 영화 syncLog 초기화 테스트
     *
     * Mlog SyncLog nextKey = 1이 되어야한다.
     *
     * Mlog SyncLog updateAt = 금일 이여야한다.
     *
     * Mlog 영화는 전부 삭제되어야한다.
     *
     */
    @Test
    fun clearMlogMoviesUpdateSyncLogUpdatedAtTest() = runTest{
        //given
        settingMockData()
        val moviePagingSource = movieRoomDataSourceImpl.getMovie(WatchProvider.MLOG_ID)

        //when
        movieRoomDataSourceImpl.clearMlogMoviesUpdateSyncLogUpdatedAt()

        //then
        val syncLogData = db.syncLogDao().getSyncLog(SyncLogType.Mlog_Movie)

        val expectedMovieData = PagingSource.LoadResult.Page<Int,MovieVO>(
            data = listOf(),
            prevKey = null,
            nextKey = null,
            itemsBefore = 0,
            itemsAfter = 0
        )
        val movieData: PagingSource.LoadResult<Int, MovieVO> = moviePagingSource.load(PagingSource.LoadParams.Refresh(
            key = 0,
            loadSize = 1,
            placeholdersEnabled = false
        ))

        assertEquals(syncLogData.nextKey, 1)
        assertEquals(syncLogData.updatedAt, System.currentTimeMillis().toDateFormat())
        assertEquals(expectedMovieData,movieData)

    }

    /**
     * Watcha 영화 syncLog 초기화 테스트
     *
     * Watcha SyncLog nextKey = 1이 되어야한다.
     *
     * Watcha SyncLog updateAt = 금일 이여야한다.
     *
     * Watcha 영화는 전부 삭제되어야한다.
     *
     */
    @Test
    fun clearWatchaMoviesUpdateSyncLogUpdatedAtTest() = runTest{
        //given
        settingMockData()
        val moviePagingSource = movieRoomDataSourceImpl.getMovie(WatchProvider.WATCHA_ID)

        //when
        movieRoomDataSourceImpl.clearWatchaMoviesUpdateSyncLogUpdatedAt()

        //then
        val syncLogData = db.syncLogDao().getSyncLog(SyncLogType.Watcha_Movie)

        val expectedMovieData = PagingSource.LoadResult.Page<Int,MovieVO>(
            data = listOf(),
            prevKey = null,
            nextKey = null,
            itemsBefore = 0,
            itemsAfter = 0
        )
        val movieData: PagingSource.LoadResult<Int, MovieVO> = moviePagingSource.load(PagingSource.LoadParams.Refresh(
            key = 0,
            loadSize = 1,
            placeholdersEnabled = false
        ))

        assertEquals(syncLogData.nextKey, 1)
        assertEquals(syncLogData.updatedAt, System.currentTimeMillis().toDateFormat())
        assertEquals(expectedMovieData,movieData)

    }

    /**
     * Netflix 영화 syncLog 초기화 테스트
     *
     * Netflix SyncLog nextKey = 1이 되어야한다.
     *
     * Netflix SyncLog updateAt = 금일 이여야한다.
     *
     * Netflix 영화는 전부 삭제되어야한다.
     *
     */
    @Test
    fun clearNetflixMoviesUpdateSyncLogUpdatedAtTest() = runTest{
        //given
        settingMockData()
        val moviePagingSource = movieRoomDataSourceImpl.getMovie(WatchProvider.NETFLIX_ID)

        //when
        movieRoomDataSourceImpl.clearNetflixMoviesUpdateSyncLogUpdatedAt()

        //then
        val syncLogData = db.syncLogDao().getSyncLog(SyncLogType.Netflix_Movie)

        val expectedMovieData = PagingSource.LoadResult.Page<Int,MovieVO>(
            data = listOf(),
            prevKey = null,
            nextKey = null,
            itemsBefore = 0,
            itemsAfter = 0
        )
        val movieData: PagingSource.LoadResult<Int, MovieVO> = moviePagingSource.load(PagingSource.LoadParams.Refresh(
            key = 0,
            loadSize = 1,
            placeholdersEnabled = false
        ))

        assertEquals(syncLogData.nextKey, 1)
        assertEquals(syncLogData.updatedAt, System.currentTimeMillis().toDateFormat())
        assertEquals(expectedMovieData,movieData)

    }

    /**
     * updateMoviesAndSyncLogNextKey 테스트
     *
     * updateMoviesAndSyncLogNextKey를 이용하여 영화, 태그, 영화제공사 데이터를 제공하면
     *
     * 영화, 태그, 영화제공사 데이터가 잘 들어가야하며 SyncLog의 nextKey 값은 1 증가하여야한다.
     */
    @Test
    fun updateMoviesAndSyncLogNextKeyTest() = runTest {
        //given
        val movieDao = db.movieDao()
        val tagDao = db.tagDao()
        val syncLogDao = db.syncLogDao()
        val watchProviderDao = db.watchProviderDao()

        val netflixWatchProviderData = mockWatchProviderEntities.filter { it.watchProviderId == WatchProvider.NETFLIX_ID }
        val netflixMovieData = mockMovieEntities.filter { movie ->
            netflixWatchProviderData.find { it.movieId == movie.id } != null
        }
        val netflixGenreData = mockGenreEntities.filter { genre ->
            netflixMovieData.find { it.id == genre.movieId } != null
        }

        val nextKey = 1
        syncLogDao.upsertSyncLog(
            SyncLogEntity(
                id = 1,
                type = SyncLogType.Netflix_Movie,
                nextKey = nextKey,
                createdAt = "2023-11-10",
                updatedAt = "2023-11-18"
            ),
        )

        //when
        movieRoomDataSourceImpl.updateMoviesAndSyncLogNextKey(
            movieEntities = netflixMovieData,
            genres = netflixGenreData.groupBy { it.movieId }.values.map { it.map { it.genreId } },
            syncLogType = SyncLogType.Netflix_Movie,
            currentKey = nextKey,
        )

        //then
        val movieDataResult = movieDao.getMovies().first()
        val tagDataResult = tagDao.getTags().first()
        val watchProviderDataResult = watchProviderDao.getWatchProviders().first()
        val syncLogDataResult = syncLogDao.getSyncLog(SyncLogType.Netflix_Movie)

        assertEquals(movieDataResult, netflixMovieData)
        assertEquals(tagDataResult, netflixGenreData)
        assertEquals(watchProviderDataResult.map { it.movieId }, netflixWatchProviderData.map { it.movieId })
        assertEquals(syncLogDataResult.nextKey, nextKey + 1)
    }
}