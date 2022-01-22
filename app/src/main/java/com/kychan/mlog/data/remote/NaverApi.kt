package com.kychan.mlog.data.remote

import com.kychan.mlog.data.remote.model.SearchMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverApi {

    @GET("v1/search/movie.json")
    fun getSearchMovie(
        @Header("X-Naver-Client-Id") clientId: String = "kHLvopePGjYBNBI1P4Us",
        @Header("X-Naver-Client-Secret") clientSecret: String = "GWjKNlXW_n",
        @Query("query") query: String,
        @Query("start") start: Int,
        @Query("display") display: Int
    ): Call<SearchMovieResponse>
}