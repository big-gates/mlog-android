package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.model.*

interface HomeRepository {
    suspend fun getMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ): List<Movie>

    suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProviders: WatchProviders
    ): List<Movie>
}