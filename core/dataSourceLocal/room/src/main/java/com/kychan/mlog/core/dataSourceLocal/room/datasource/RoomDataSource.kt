package com.kychan.mlog.core.dataSourceLocal.room.datasource

import androidx.paging.PagingSource
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow

interface RoomDataSource {
    fun getMLogMovie(): PagingSource<Int, MlogMovieEntity>
    fun getNetflixMovie(): PagingSource<Int, NetflixMovieEntity>
    fun getWatchaMovie(): PagingSource<Int, WatchaMovieEntity>
    fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>>
    suspend fun getSyncLog(syncLogType: SyncLogType): SyncLogEntity
    suspend fun upsertSyncLogAndMlogMovies(
        movieEntities: List<MlogMovieEntity>,
        syncLogEntity: SyncLogEntity
    )

    suspend fun upsertSyncLogAndNetflixMovies(
        movieEntities: List<NetflixMovieEntity>,
        syncLogEntity: SyncLogEntity
    )

    suspend fun upsertSyncLogAndWatchaMovies(
        movieEntities: List<WatchaMovieEntity>,
        syncLogEntity: SyncLogEntity
    )
}