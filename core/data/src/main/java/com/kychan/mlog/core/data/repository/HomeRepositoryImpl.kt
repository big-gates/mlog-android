package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.data.mapper.toDomain
import com.kychan.mlog.core.dataSourceRemote.http.datasource.tmdb.TMDBDataSource
import com.kychan.mlog.core.model.*
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val tmdbDataSource: TMDBDataSource
): HomeRepository {

    override suspend fun getMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ): List<Movie> {
        return tmdbDataSource.getMoviePopular(
            page,
            language,
            watchRegion
        ).toDomain()
    }

    override suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProviders: WatchProviders
    ): List<Movie> {
        return tmdbDataSource.getMoviePopularWithProvider(
            page,
            language,
            watchRegion,
            withWatchProviders
        ).toDomain()
    }
}