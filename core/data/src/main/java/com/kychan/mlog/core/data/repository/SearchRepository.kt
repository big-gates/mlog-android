package com.kychan.mlog.core.data.repository

import androidx.paging.PagingData
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.RecentSearch
import com.kychan.mlog.core.model.WatchRegion
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSearch(
        language: Language,
        watchRegion: WatchRegion,
        query: String,
    ): Flow<PagingData<Movie>>

    suspend fun updateRecentSearch(text: String)

    fun getRecentSearch(): Flow<List<RecentSearch>>

    suspend fun deleteAllRecentSearch()

    suspend fun deleteRecentSearch(id: Int)
}