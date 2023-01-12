package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.model.*
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getPopularMoviesWithCategory(watchProviders: WatchProviders): Flow<List<Movie>>

    suspend fun updateMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    )

    suspend fun updateMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProviders: WatchProviders
    )
}