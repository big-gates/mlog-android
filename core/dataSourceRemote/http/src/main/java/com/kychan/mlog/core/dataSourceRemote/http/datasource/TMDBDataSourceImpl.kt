package com.kychan.mlog.core.dataSourceRemote.http.datasource

import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchProvider
import com.kychan.mlog.core.model.WatchRegion
import javax.inject.Inject

class TMDBDataSourceImpl @Inject constructor(
    private val tmdbApi: RetrofitTMDBApi
): TMDBDataSource {
    override suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProvider: WatchProvider
    ): MovieDiscoverRes {
        return tmdbApi.getMoviePopularWithProvider(page, language, watchRegion, withWatchProvider)
    }

    override suspend fun getMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ): MoviePopularRes {
        return tmdbApi.getMoviePopular(page, language, watchRegion)
    }

}