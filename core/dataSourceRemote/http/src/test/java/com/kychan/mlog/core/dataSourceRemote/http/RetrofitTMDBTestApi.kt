package com.kychan.mlog.core.dataSourceRemote.http

import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDetailRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieSearchRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion

class RetrofitTMDBTestApi: RetrofitTMDBApi {
    override suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProvider: Int
    ): MovieDiscoverRes {
        return when(withWatchProvider){
            97 -> {
                readFile("discover_movie_watcha_${page}.json", MovieDiscoverRes::class.java)
            }

            8 -> {
                readFile("discover_movie_netflix_${page}.json", MovieDiscoverRes::class.java)
            }

            else -> throw IllegalArgumentException("Type not supported")
        }
    }

    override suspend fun getMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ): MoviePopularRes {
        return readFile("discover_movie_mlog_${page}.json", MoviePopularRes::class.java)
    }

    override suspend fun getSearch(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        query: String
    ): MovieSearchRes {
        return when(query) {
            "어벤져" -> readFile("search_movie_avg_${page}_${language}_${watchRegion}.json", MovieSearchRes::class.java)
            else -> readFile("search_movie_empty_${page}_${language}_${watchRegion}.json", MovieSearchRes::class.java)
        }
    }

    override suspend fun getMovieDetail(
        movieId: Int,
        language: Language,
        appendToResponse: String
    ): MovieDetailRes {
        return readFile("detail_movie_${movieId}_${language}.json", MovieDetailRes::class.java)
    }
}
