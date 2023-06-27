package com.kychan.mlog.core.dataSourceRemote.http.api

import com.kychan.mlog.core.dataSourceRemote.http.model.*
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchProvider
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
        @Query("with_watch_providers") withWatchProvider: Int
    ): MovieDiscoverRes

    @GET("/3/movie/popular")
    suspend fun getMoviePopular(
        @Query("page") page: Int,
        @Query("language") language: Language,
        @Query("region") watchRegion: WatchRegion,
    ): MoviePopularRes

    @GET("/3/search/multi")
    suspend fun getSearch(
        @Query("page") page: Int,
        @Query("language") language: Language,
        @Query("region") watchRegion: WatchRegion,
        @Query("query") query: String,
    ): MovieSearchRes

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: Language,
        @Query("append_to_response") appendToResponse: String,
    ): MovieDetailRes
}