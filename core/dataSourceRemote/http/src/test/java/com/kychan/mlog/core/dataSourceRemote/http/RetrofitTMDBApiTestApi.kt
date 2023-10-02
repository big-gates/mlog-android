package com.kychan.mlog.core.dataSourceRemote.http

import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDetailRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieSearchRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion

class RetrofitTMDBApiTestApi: RetrofitTMDBApi {
    override suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProvider: Int
    ): MovieDiscoverRes {
        TODO("Not yet implemented")
    }

    override suspend fun getMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ): MoviePopularRes {
        TODO("Not yet implemented")
    }

    override suspend fun getSearch(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        query: String
    ): MovieSearchRes {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(
        movieId: Int,
        language: Language,
        appendToResponse: String
    ): MovieDetailRes {
        TODO("Not yet implemented")
    }
}