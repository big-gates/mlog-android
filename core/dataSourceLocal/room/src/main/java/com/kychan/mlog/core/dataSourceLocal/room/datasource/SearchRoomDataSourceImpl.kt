package com.kychan.mlog.core.dataSourceLocal.room.datasource

import com.kychan.mlog.core.dataSourceLocal.room.dao.SearchDao
import com.kychan.mlog.core.dataSourceLocal.room.model.RecentSearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRoomDataSourceImpl @Inject constructor(
    private val searchDao: SearchDao,
): SearchRoomDataSource {
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