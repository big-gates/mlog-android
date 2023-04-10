package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.data.mapper.toRecentSearchEntity
import com.kychan.mlog.core.dataSourceLocal.room.datasource.RoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.RecentSearchEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.toDomain
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.model.RecentSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val tmdbDataSource: TMDBDataSource,
    private val roomDataSource: RoomDataSource
): SearchRepository {

    override suspend fun updateRecentSearch(text: String) {
        roomDataSource.updateRecentSearch(text.toRecentSearchEntity())
    }

    override fun getRecentSearch(): Flow<List<RecentSearch>> {
        return roomDataSource.getRecentSearch().map { it.map(RecentSearchEntity::toDomain) }
    }

    override suspend fun deleteAllRecentSearch() {
        roomDataSource.deleteAllRecentSearch()
    }

    override suspend fun deleteRecentSearch(id: Int) {
        roomDataSource.deleteRecentSearch(id)
    }
}