package com.kychan.mlog.core.dataSourceLocal.room.datasource

import androidx.paging.PagingSource
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow
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

    override suspend fun clearMlogMoviesUpdateSyncLogUpdatedAt() {
        movieDao.clearMlogMoviesUpdateSyncLogUpdatedAt()
    }

    override suspend fun clearNetflixMoviesUpdateSyncLogUpdatedAt() {
        movieDao.clearNetflixMoviesUpdateSyncLogUpdatedAt()
    }

    override suspend fun clearWatchaMoviesUpdateSyncLogUpdatedAt() {
        movieDao.clearWatchaMoviesUpdateSyncLogUpdatedAt()
    }

    override suspend fun getSyncLog(syncLogType: SyncLogType): SyncLogEntity {
        return movieDao.getSyncLog(syncLogType)
    }

    override suspend fun updateMlogMoviesAndSyncLogNextKey(
        movieEntities: List<MlogMovieEntity>,
        nextKey: Int,
    ) {
        movieDao.updateMlogMoviesAndSyncLogNextKey(movieEntities, nextKey)
    }

    override suspend fun updateNetflixMoviesAndSyncLogNextKey(
        movieEntities: List<NetflixMovieEntity>,
        nextKey: Int,
    ) {
        movieDao.updateNetflixMoviesAndSyncLogNextKey(movieEntities,nextKey)
    }

    override suspend fun updateWatchaMoviesAndSyncLogNextKey(
        movieEntities: List<WatchaMovieEntity>,
        nextKey: Int,
    ) {
        movieDao.updateWatchaMoviesAndSyncLogNextKey(movieEntities,nextKey)
    }

    override fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>> {
        return movieDao.getMyRatedMovies()
    }
}