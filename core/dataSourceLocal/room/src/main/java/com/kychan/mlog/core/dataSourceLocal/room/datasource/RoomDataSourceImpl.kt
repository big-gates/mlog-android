package com.kychan.mlog.core.dataSourceLocal.room.datasource

import androidx.paging.PagingSource
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SearchDao
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val searchDao: SearchDao,
): RoomDataSource {

    override fun getMovie(movieTypeId: Int): PagingSource<Int, MovieVO> {
        return movieDao.getMovie(movieTypeId)
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

    override suspend fun updateMoviesAndSyncLogNextKey(
        movieEntities: List<MovieEntity>,
        genres: List<List<Int>>,
        syncLogType: SyncLogType,
        currentKey: Int
    ) {
        movieDao.updateMoviesAndSyncLogNextKey(
            movieEntities,
            genres,
            syncLogType,
            currentKey
        )
    }

    override suspend fun updateRecentSearch(recentSearchEntity: RecentSearchEntity) {
        searchDao.upsertRecentSearch(recentSearchEntity)
    }

    override fun getRecentSearches(): Flow<List<RecentSearchEntity>> {
        return searchDao.getRecentSearches()
    }

    override fun getRecentSearch(text: String): Flow<RecentSearchEntity> {
        return searchDao.getRecentSearch(text)
    }

    override suspend fun deleteAllRecentSearch() {
        searchDao.deleteAllRecentSearch()
    }

    override suspend fun deleteRecentSearch(id: Int) {
        searchDao.deleteRecentSearch(id)
    }
}