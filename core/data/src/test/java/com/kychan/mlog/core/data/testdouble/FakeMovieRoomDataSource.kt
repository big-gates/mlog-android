package com.kychan.mlog.core.data.testdouble

import androidx.paging.PagingSource
import com.kychan.mlog.core.common.extenstions.toDateFormat
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SyncLogDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.WatchProviderDao
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieVO
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogType
import com.kychan.mlog.core.model.WatchProvider
import kotlinx.coroutines.flow.first

class FakeMovieRoomDataSource(
    private val movieDao: MovieDao,
    private val syncLogDao: SyncLogDao,
    private val watchProviderDao: WatchProviderDao,
): MovieRoomDataSource {
    override fun getMovie(movieTypeId: Int): PagingSource<Int, MovieVO> {
        return movieDao.getMovies(movieTypeId)
    }

    override suspend fun clearMlogMoviesUpdateSyncLogUpdatedAt() {
        val ids = watchProviderDao.getMovieIds(WatchProvider.MLOG_ID).first()
        movieDao.deleteMovies(ids)
        clearSyncLog(SyncLogType.Mlog_Movie)
    }

    override suspend fun clearNetflixMoviesUpdateSyncLogUpdatedAt() {
        TODO("Not yet implemented")
    }

    override suspend fun clearWatchaMoviesUpdateSyncLogUpdatedAt() {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    private suspend fun clearSyncLog(syncLogType: SyncLogType){
        val syncLog = getSyncLog(syncLogType)
        syncLogDao.upsertSyncLog(syncLog.copy(
            nextKey = 1,
            updatedAt = System.currentTimeMillis().toDateFormat()
        ))
    }
}