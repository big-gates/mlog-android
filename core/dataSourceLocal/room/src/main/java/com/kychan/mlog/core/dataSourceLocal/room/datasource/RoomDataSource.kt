package com.kychan.mlog.core.dataSourceLocal.room.datasource

import androidx.paging.PagingSource
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow

interface RoomDataSource {
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
    suspend fun updateRecentSearch(
        recentSearchEntity: RecentSearchEntity,
    )

    fun getRecentSearches(): Flow<List<RecentSearchEntity>>

    fun getRecentSearch(text: String): Flow<RecentSearchEntity>

    suspend fun deleteAllRecentSearch()

    suspend fun deleteRecentSearch(id: Int)
}