package com.kychan.mlog.core.dataSourceRemote.http.datasource.tmdb

import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.TvSeriesDetailRes
import com.kychan.mlog.core.dataSourceRemote.http.model.TvSeriesRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchProviders
import com.kychan.mlog.core.model.WatchRegion

interface TMDBDataSource {

    suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProviders: WatchProviders
    ): MovieDiscoverRes

    suspend fun getTvSeriesPopular(
        page: Int,
        language: String
    ): TvSeriesRes

    suspend fun getTvSeriesDetail(
        tvSeriesId: Int,
        language: String
    ): TvSeriesDetailRes
}