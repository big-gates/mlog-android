package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.model.*

interface HomeRepository {
    fun getBoxOfficeRanking()
    suspend fun getWatchaRanking(page: Int, language: String): List<TvSeriesEntity>
    suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProviders: WatchProviders
    ): List<Movie>
}