package com.kychan.mlog.core.dataSourceRemote.http.fake

import JvmUnitTestFakeAssetManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDetailRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieSearchRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okio.use

@OptIn(ExperimentalSerializationApi::class)
class RetrofitTMDBTestApi: RetrofitTMDBApi {

    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager
    private val json = Json

    override suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProvider: Int
    ): MovieDiscoverRes {
        return when(withWatchProvider){
            97 -> {
                assets.open("discover_movie_watcha_${page}.json").use(json::decodeFromStream)
            }

            8 -> {
                assets.open("discover_movie_netflix_${page}.json").use(json::decodeFromStream)
            }

            else -> throw IllegalArgumentException("Type not supported")
        }
    }

    override suspend fun getMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ): MoviePopularRes {
        return assets.open("discover_movie_mlog_${page}.json").use(json::decodeFromStream)
    }

    override suspend fun getSearch(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        query: String
    ): MovieSearchRes {
        return when(query) {
            "어벤져" -> assets.open("search_movie_avg_${page}_${language}_${watchRegion}.json").use(json::decodeFromStream)
            else -> assets.open("search_movie_${page}_${language}_${watchRegion}_empty.json").use(json::decodeFromStream)
        }
    }

    override suspend fun getMovieDetail(
        movieId: Int,
        language: Language,
        appendToResponse: String
    ): MovieDetailRes {
        return assets.open("detail_movie_${movieId}_${language}.json").use(json::decodeFromStream)
    }
}
