package com.kychan.mlog.core.dataSourceRemote.http.datasource.tmdb

import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.TvSeriesDetailRes
import com.kychan.mlog.core.dataSourceRemote.http.model.TvSeriesRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchProviders
import com.kychan.mlog.core.model.WatchRegion
import javax.inject.Inject

class TMDBDataSourceImpl @Inject constructor(
    private val tmdbApi: RetrofitTMDBApi
): TMDBDataSource {
    override suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProviders: WatchProviders
    ): MovieDiscoverRes {
        return tmdbApi.getMoviePopularWithProvider(page, language, watchRegion, withWatchProviders)
    }

    override suspend fun getTvSeriesPopular(page: Int, language: String): TvSeriesRes {
        return tmdbApi.getTvSeriesPopular(page, language)
    }

    override suspend fun getTvSeriesDetail(tvSeriesId: Int, language: String): TvSeriesDetailRes {
        return tmdbApi.getTvSeriesDetail(tvSeriesId, language)
    }

}