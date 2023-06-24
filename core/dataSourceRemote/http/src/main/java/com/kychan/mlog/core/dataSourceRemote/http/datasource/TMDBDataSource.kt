package com.kychan.mlog.core.dataSourceRemote.http.datasource

import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDetailRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieSearchRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion

interface TMDBDataSource {

    suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProviderId: Int
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