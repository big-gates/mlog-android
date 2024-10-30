package com.kychan.mlog.core.data.testdouble

import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDetailRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieSearchRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion
import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBTestApi

class FakeTmdbDataSource:TMDBDataSource {

    private val source = RetrofitTMDBTestApi()

    override suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProviderId: Int
    ): MovieDiscoverRes {
        return source.getMoviePopularWithProvider(page, language, watchRegion, withWatchProviderId)
    }

    override suspend fun getMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ): MoviePopularRes {
        return source.getMoviePopular(page, language, watchRegion)
    }

    override suspend fun getSearch(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        query: String
    ): MovieSearchRes {
        return source.getSearch(page, language, watchRegion, query)
    }

    override suspend fun getMovieDetail(
        movieId: Int,
        language: Language,
        appendToResponse: String
    ): MovieDetailRes {
        return source.getMovieDetail(movieId, language, appendToResponse)
    }
}