package com.kychan.mlog.core.dataSourceLocal.room.datasource

import androidx.paging.PagingSource
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow

interface MovieRoomDataSource {
    fun getMovie(movieTypeId: Int): PagingSource<Int, MovieVO>
    suspend fun clearMlogMoviesUpdateSyncLogUpdatedAt()
    suspend fun clearNetflixMoviesUpdateSyncLogUpdatedAt()
    suspend fun clearWatchaMoviesUpdateSyncLogUpdatedAt()
    suspend fun getSyncLog(syncLogType: SyncLogType): SyncLogEntity
    suspend fun updateMoviesAndSyncLogNextKey(
        movieEntities: List<MovieEntity>,
        genres: List<List<Int>>,
        syncLogType: SyncLogType,
        currentKey: Int,
    )
}