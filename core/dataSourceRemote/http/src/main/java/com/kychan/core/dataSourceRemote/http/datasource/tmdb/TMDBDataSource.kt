package com.kychan.core.dataSourceRemote.http.datasource.tmdb

import com.kychan.core.dataSourceRemote.http.model.TvSeriesDetailRes
import com.kychan.core.dataSourceRemote.http.model.TvSeriesRes

interface TMDBDataSource {

    suspend fun getTvSeriesPopular(
        page: Int,
        language: String
    ): TvSeriesRes

    suspend fun getTvSeriesDetail(
        tvSeriesId: Int,
        language: String
    ): TvSeriesDetailRes
}