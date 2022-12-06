package com.kychan.core.dataSourceRemote.http.datasource.tmdb

import com.kychan.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.core.dataSourceRemote.http.model.TvSeriesDetailRes
import com.kychan.core.dataSourceRemote.http.model.TvSeriesRes
import javax.inject.Inject

class TMDBDataSourceImpl @Inject constructor(
    private val tmdbApi: RetrofitTMDBApi
): TMDBDataSource {
    override suspend fun getTvSeriesPopular(page: Int, language: String): TvSeriesRes {
        return tmdbApi.getTvSeriesPopular(page, language)
    }

    override suspend fun getTvSeriesDetail(tvSeriesId: Int, language: String): TvSeriesDetailRes {
        return tmdbApi.getTvSeriesDetail(tvSeriesId, language)
    }

}