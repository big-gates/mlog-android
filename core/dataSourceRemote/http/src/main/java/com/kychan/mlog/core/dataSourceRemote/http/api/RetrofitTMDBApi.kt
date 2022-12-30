package com.kychan.mlog.core.dataSourceRemote.http.api

import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.TvSeriesDetailRes
import com.kychan.mlog.core.dataSourceRemote.http.model.TvSeriesRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchProviders
import com.kychan.mlog.core.model.WatchRegion
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitTMDBApi {

    @GET("/3/discover/movie")
    suspend fun getMoviePopularWithProvider(
        @Query("page") page: Int,
        @Query("language") language: Language,
        @Query("watch_region") watchRegion: WatchRegion,
        @Query("with_watch_providers") withWatchProviders: WatchProviders
    ): MovieDiscoverRes

    @GET("/3/tv/popular")
    suspend fun getTvSeriesPopular(
        @Query("page") page: Int,
        @Query("language") language: String
    ): TvSeriesRes

    @GET("/3/tv/{tvSeriesId}")
    suspend fun getTvSeriesDetail(
        @Path("tvSeriesId") tvSeriesId: Int,
        @Query("language") language: String
    ): TvSeriesDetailRes

}