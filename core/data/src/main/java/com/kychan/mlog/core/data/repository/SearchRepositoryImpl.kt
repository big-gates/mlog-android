package com.kychan.mlog.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kychan.mlog.core.data.mapper.toRecentSearchEntity
import com.kychan.mlog.core.data.mediator.SearchMovieMediator
import com.kychan.mlog.core.dataSourceLocal.room.datasource.SearchRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.RecentSearchEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.toDomain
import com.kychan.mlog.core.dataSourceLocal.room.model.update
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.RecentSearch
import com.kychan.mlog.core.model.WatchRegion
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val tmdbDataSource: TMDBDataSource,
    private val searchRoomDataSource: SearchRoomDataSource
): SearchRepository {
    override fun getSearch(
        language: Language,
        watchRegion: WatchRegion,
        query: String
    ): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(pageSize = 20)
        ) {
            SearchMovieMediator(
                tmdbDataSource,
                query,
                language,
                watchRegion
            )
        }.flow
    }

    override suspend fun updateRecentSearch(text: String) {
        searchRoomDataSource.getRecentSearch(text).firstOrNull()?.let {
            searchRoomDataSource.updateRecentSearch(it.update())
        }?: searchRoomDataSource.updateRecentSearch(text.toRecentSearchEntity())
    }

    override fun getRecentSearch(): Flow<List<RecentSearch>> {
        return searchRoomDataSource.getRecentSearches().map { it.map(RecentSearchEntity::toDomain) }
    }

    override suspend fun deleteAllRecentSearch() {
        searchRoomDataSource.deleteAllRecentSearch()
    }

    override suspend fun deleteRecentSearch(id: Int) {
        searchRoomDataSource.deleteRecentSearch(id)
    }
}