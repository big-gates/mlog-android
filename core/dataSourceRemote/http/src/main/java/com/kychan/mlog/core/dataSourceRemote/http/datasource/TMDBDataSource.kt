package com.kychan.mlog.core.dataSourceRemote.http.datasource

import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDetailRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieSearchRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchProvider
import com.kychan.mlog.core.model.WatchRegion
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBDataSource {

    suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProvider: WatchProvider
    ): MovieDiscoverRes

    suspend fun getMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
    ): MoviePopularRes

    suspend fun getSearch(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        query: String,
    ): MovieSearchRes

    suspend fun getMovieDetail(
        movieId: Int,
        language: Language,
        appendToResponse: String,
    ): MovieDetailRes
}