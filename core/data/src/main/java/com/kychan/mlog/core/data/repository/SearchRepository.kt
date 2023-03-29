package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.model.RecentSearch
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun updateRecentSearch(text: String)

    fun getRecentSearch(): Flow<List<RecentSearch>>

    suspend fun deleteAllRecentSearch()

    suspend fun deleteRecentSearch(id: Int)
}