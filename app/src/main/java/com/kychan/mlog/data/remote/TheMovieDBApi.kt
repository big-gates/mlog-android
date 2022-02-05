package com.kychan.mlog.data.remote

import com.kychan.mlog.BuildConfig
import com.kychan.mlog.data.remote.model.SearchMovieByTheMovieDBResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBApi {

    @GET("3/search/movie")
    fun getSearchMovie(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DB_API_KEY,
        @Query("language") language: String = LANGUAGE_KOR,
        @Query("query") query: String,
        @Query("page") page: Int?
    ): Call<SearchMovieByTheMovieDBResponse>

    companion object {
        private const val LANGUAGE_KOR = "ko-KR"
    }
}
