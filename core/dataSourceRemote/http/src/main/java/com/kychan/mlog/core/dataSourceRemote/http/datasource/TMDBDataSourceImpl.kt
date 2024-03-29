package com.kychan.mlog.core.dataSourceRemote.http.datasource

import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDetailRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieSearchRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion
import javax.inject.Inject

class TMDBDataSourceImpl @Inject constructor(
    private val tmdbApi: RetrofitTMDBApi
): TMDBDataSource {
    override suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProviderId: Int
    ): MovieDiscoverRes {
        return tmdbApi.getMoviePopularWithProvider(page, language, watchRegion, withWatchProviderId)
    }

    override suspend fun getMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ): MoviePopularRes {
        return tmdbApi.getMoviePopular(page, language, watchRegion)
    }

    override suspend fun getSearch(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        query: String
    ): MovieSearchRes {
        return tmdbApi.getSearch(page, language, watchRegion, query)
    }

    override suspend fun getMovieDetail(
        movieId: Int,
        language: Language,
        appendToResponse: String
    ): MovieDetailRes {
        return tmdbApi.getMovieDetail(movieId, language, appendToResponse)
    }

}