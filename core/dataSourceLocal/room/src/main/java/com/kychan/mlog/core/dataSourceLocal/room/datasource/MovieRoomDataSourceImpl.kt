package com.kychan.mlog.core.dataSourceLocal.room.datasource

import androidx.paging.PagingSource
import androidx.room.Transaction
import com.kychan.mlog.core.common.extenstions.toDateFormat
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SearchDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SyncLogDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.TagDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.WatchProviderDao
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import com.kychan.mlog.core.model.WatchProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val DEFAULT_RESULT_COUNT = 20
class MovieRoomDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val tagDao: TagDao,
    private val syncLogDao: SyncLogDao,
    private val watchProviderDao: WatchProviderDao
): MovieRoomDataSource {

    override fun getMovie(movieTypeId: Int): PagingSource<Int, MovieVO> {
        return movieDao.getMovies(movieTypeId)
    }

    @Transaction
    override suspend fun clearMlogMoviesUpdateSyncLogUpdatedAt() {
        val ids = watchProviderDao.getMovieIds(WatchProvider.MLOG_ID).first()
        movieDao.deleteMovies(ids)
        clearSyncLog(SyncLogType.Mlog_Movie)
    }

    @Transaction
    override suspend fun clearNetflixMoviesUpdateSyncLogUpdatedAt() {
        val ids = watchProviderDao.getMovieIds(WatchProvider.NETFLIX_ID).first()
        movieDao.deleteMovies(ids)
        clearSyncLog(SyncLogType.Netflix_Movie)
    }

    override suspend fun clearWatchaMoviesUpdateSyncLogUpdatedAt() {
        val ids = watchProviderDao.getMovieIds(WatchProvider.WATCHA_ID).first()
        movieDao.deleteMovies(ids)
        clearSyncLog(SyncLogType.Watcha_Movie)
    }

    override suspend fun getSyncLog(syncLogType: SyncLogType): SyncLogEntity {
        return syncLogDao.getSyncLog(syncLogType)
    }

    override suspend fun updateMoviesAndSyncLogNextKey(
        movieEntities: List<MovieEntity>,
        genres: List<List<Int>>,
        syncLogType: SyncLogType,
        currentKey: Int
    ) {
        movieDao.upsertMovies(movieEntities)
        val tagEntities = movieEntities.mapIndexed { index, movie ->
            genres[index].map {
                GenresEntity(genreId = it, movieId = movie.id)
            }
        }.flatten()

        val watchProviderEntities = movieEntities.mapIndexed { index, movie ->
            when(syncLogType){
                SyncLogType.Netflix_Movie -> WatchProviderEntity(
                    movieId = movie.id,
                    watchProviderId = WatchProvider.NETFLIX_ID,
                    rank = ((currentKey * DEFAULT_RESULT_COUNT) - DEFAULT_RESULT_COUNT) + (index + 1),
                )
                SyncLogType.Watcha_Movie -> WatchProviderEntity(
                    movieId = movie.id,
                    watchProviderId = WatchProvider.WATCHA_ID,
                    rank = ((currentKey * DEFAULT_RESULT_COUNT) - DEFAULT_RESULT_COUNT) + (index + 1),
                )
                SyncLogType.Mlog_Movie -> WatchProviderEntity(
                    movieId = movie.id,
                    watchProviderId = WatchProvider.MLOG_ID,
                    rank = ((currentKey * DEFAULT_RESULT_COUNT) - DEFAULT_RESULT_COUNT) + (index + 1),
                )
            }
        }
        watchProviderDao.upsertWatchProviders(watchProviderEntities)
        tagDao.upsertTags(tagEntities)
        val syncLog = getSyncLog(syncLogType)
        syncLogDao.upsertSyncLog(syncLog.copy(nextKey = currentKey + 1))

    }

    private suspend fun clearSyncLog(syncLogType: SyncLogType){
        val syncLog = getSyncLog(syncLogType)
        syncLogDao.upsertSyncLog(syncLog.copy(
            nextKey = 1,
            updatedAt = System.currentTimeMillis().toDateFormat()
        ))
    }
}