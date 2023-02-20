package com.kychan.mlog.core.dataSourceLocal.room.datasource

import androidx.paging.PagingSource
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import javax.inject.Inject

class RoomDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
): RoomDataSource {
    override fun getMLogMovie(): PagingSource<Int, MlogMovieEntity> {
        return movieDao.getMlogMovie()
    }

    override fun getNetflixMovie(): PagingSource<Int, NetflixMovieEntity> {
        return movieDao.getNetflixMovie()
    }

    override fun getWatchaMovie(): PagingSource<Int, WatchaMovieEntity> {
        return movieDao.getWatchaMovie()
    }

    override suspend fun getSyncLog(syncLogType: SyncLogType): SyncLogEntity {
        return movieDao.getSyncLog(syncLogType)
    }

    override suspend fun upsertSyncLogAndMlogMovies(
        movieEntities: List<MlogMovieEntity>,
        syncLogEntity: SyncLogEntity
    ) {
        movieDao.upsertSyncLogAndMlogMovies(movieEntities, syncLogEntity)
    }

    override suspend fun upsertSyncLogAndNetflixMovies(
        movieEntities: List<NetflixMovieEntity>,
        syncLogEntity: SyncLogEntity
    ) {
        movieDao.upsertSyncLogAndNetflixMovies(movieEntities,syncLogEntity)
    }

    override suspend fun upsertSyncLogAndWatchaMovies(
        movieEntities: List<WatchaMovieEntity>,
        syncLogEntity: SyncLogEntity
    ) {
        movieDao.upsertSyncLogAndWatchaMovies(movieEntities,syncLogEntity)
    }

    override fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>> {
        return movieDao.getMyRatedMovies()
    }
}