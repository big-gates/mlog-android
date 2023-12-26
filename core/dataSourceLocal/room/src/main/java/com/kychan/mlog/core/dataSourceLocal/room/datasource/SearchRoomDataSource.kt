package com.kychan.mlog.core.dataSourceLocal.room.datasource

import com.kychan.mlog.core.dataSourceLocal.room.model.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

interface SearchRoomDataSource {

    suspend fun updateRecentSearch(
        recentSearchEntity: RecentSearchEntity,
    )

    fun getRecentSearches(): Flow<List<RecentSearchEntity>>

    fun getRecentSearch(text: String): Flow<RecentSearchEntity>

    suspend fun deleteAllRecentSearch()

    suspend fun deleteRecentSearch(id: Int)
}