package com.kychan.mlog.core.dataSourceRemote.http.api

import JvmUnitTestDemoAssetManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDetailRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieSearchRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion
import java.io.BufferedReader
import java.io.InputStreamReader

class RetrofitTMDBTestApi: RetrofitTMDBApi {

    val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
    override suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProvider: Int
    ): MovieDiscoverRes {
        return when(withWatchProvider){
            97 -> {
                JvmUnitTestDemoAssetManager.open("discover_movie_watcha_${page}.json").use {
                    gson.fromJson(BufferedReader(InputStreamReader(it)),MovieDiscoverRes::class.java)
                }
            }

            8 -> {
                JvmUnitTestDemoAssetManager.open("discover_movie_netflix_${page}.json").use {
                    gson.fromJson(BufferedReader(InputStreamReader(it)),MovieDiscoverRes::class.java)
                }
            }

            else -> throw IllegalArgumentException("Type not supported")
        }
    }

    override suspend fun getMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ): MoviePopularRes {
        return JvmUnitTestDemoAssetManager.open("discover_movie_mlog_${page}.json").use {
            gson.fromJson(BufferedReader(InputStreamReader(it)),MoviePopularRes::class.java)
        }
    }

    override suspend fun getSearch(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        query: String
    ): MovieSearchRes {
        return when(query) {
            "어벤져" -> JvmUnitTestDemoAssetManager.open("search_movie_avg_${page}_${language}_${watchRegion}.json").use {
                gson.fromJson(BufferedReader(InputStreamReader(it)),MovieSearchRes::class.java)
            }
            else -> JvmUnitTestDemoAssetManager.open("search_movie_${page}_${language}_${watchRegion}_empty.json").use {
                gson.fromJson(BufferedReader(InputStreamReader(it)),MovieSearchRes::class.java)
            }
        }
    }

    override suspend fun getMovieDetail(
        movieId: Int,
        language: Language,
        appendToResponse: String
    ): MovieDetailRes {
        return JvmUnitTestDemoAssetManager.open("detail_movie_${movieId}_${language}.json").use {
            gson.fromJson(BufferedReader(InputStreamReader(it)),MovieDetailRes::class.java)
        }
    }
}
