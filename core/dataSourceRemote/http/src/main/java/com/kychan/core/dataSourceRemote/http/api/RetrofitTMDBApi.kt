package com.kychan.core.dataSourceRemote.http.api

import com.kychan.core.dataSourceRemote.http.model.TvSeriesDetailRes
import com.kychan.core.dataSourceRemote.http.model.TvSeriesRes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitTMDBApi {

    //    @GET("/movie/popular")
//    suspend fun getMoviePopular(
//        @Query("page") page: Int,
//        @Query("language") language: String
//    ):

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