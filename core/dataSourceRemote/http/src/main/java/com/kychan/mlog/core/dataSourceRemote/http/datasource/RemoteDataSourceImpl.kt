package com.kychan.mlog.core.dataSourceRemote.http.datasource

import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchProviders
import com.kychan.mlog.core.model.WatchRegion
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val tmdbApi: RetrofitTMDBApi
): RemoteDataSource {
    override suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProviders: WatchProviders
    ): MovieDiscoverRes {
        return tmdbApi.getMoviePopularWithProvider(page, language, watchRegion, withWatchProviders)
    }

    override suspend fun getMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ): MoviePopularRes {
        return tmdbApi.getMoviePopular(page, language, watchRegion)
    }

}