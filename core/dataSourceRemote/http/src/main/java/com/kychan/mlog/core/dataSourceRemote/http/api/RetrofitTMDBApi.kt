package com.kychan.mlog.core.dataSourceRemote.http.api

import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchProviders
import com.kychan.mlog.core.model.WatchRegion
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitTMDBApi {

    @GET("/3/discover/movie")
    suspend fun getMoviePopularWithProvider(
        @Query("page") page: Int,
        @Query("language") language: Language,
        @Query("watch_region") watchRegion: WatchRegion,
        @Query("with_watch_providers") withWatchProviders: WatchProviders
    ): MovieDiscoverRes

    @GET("/3/movie/popular")
    suspend fun getMoviePopular(
        @Query("page") page: Int,
        @Query("language") language: Language,
        @Query("region") watchRegion: WatchRegion,
    ): MoviePopularRes

}