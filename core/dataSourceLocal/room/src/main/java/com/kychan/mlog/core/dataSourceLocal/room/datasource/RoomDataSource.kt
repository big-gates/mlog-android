package com.kychan.mlog.core.dataSourceLocal.room.datasource

import androidx.paging.PagingSource
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow

interface RoomDataSource {
    fun getMLogMovie(): PagingSource<Int, MlogMovieEntity>
    fun getNetflixMovie(): PagingSource<Int, NetflixMovieEntity>
    fun getWatchaMovie(): PagingSource<Int, WatchaMovieEntity>
    suspend fun clearMlogMoviesUpdateSyncLogUpdatedAt()
    suspend fun clearNetflixMoviesUpdateSyncLogUpdatedAt()
    suspend fun clearWatchaMoviesUpdateSyncLogUpdatedAt()
    suspend fun getSyncLog(syncLogType: SyncLogType): SyncLogEntity
    suspend fun updateMlogMoviesAndSyncLogNextKey(
        movieEntities: List<MlogMovieEntity>,
        nextKey: Int,
    )

    suspend fun updateNetflixMoviesAndSyncLogNextKey(
        movieEntities: List<NetflixMovieEntity>,
        nextKey: Int,
    )

    suspend fun updateWatchaMoviesAndSyncLogNextKey(
        movieEntities: List<WatchaMovieEntity>,
        nextKey: Int,
    )

    suspend fun updateRecentSearch(
        recentSearchEntity: RecentSearchEntity,
    )

    fun getRecentSearches(): Flow<List<RecentSearchEntity>>

    fun getRecentSearch(text: String): Flow<RecentSearchEntity>

    suspend fun deleteAllRecentSearch()

    suspend fun deleteRecentSearch(id: Int)
}