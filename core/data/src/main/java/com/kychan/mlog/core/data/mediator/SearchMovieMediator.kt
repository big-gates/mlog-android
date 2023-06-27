package com.kychan.mlog.core.data.mediator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kychan.mlog.core.data.mapper.toModel
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.MediaType
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.WatchRegion

class SearchMovieMediator(
    private val tmdbDataSource: TMDBDataSource,
    private val query: String,
    private val language: Language,
    private val watchRegion: WatchRegion
): PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = tmdbDataSource.getSearch(
                page = nextPageNumber,
                language = language,
                watchRegion = watchRegion,
                query = query
            )
            LoadResult.Page(
                data = response.toModel(
                    filters = listOf(MediaType.MOVIE, MediaType.TV)
                ),
                prevKey = null,
                nextKey = if(response.results.isEmpty()) null else response.page + 1
            )
        } catch(e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}