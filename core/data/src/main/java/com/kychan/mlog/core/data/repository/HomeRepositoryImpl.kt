package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.data.mapper.toEntity
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.toDomain
import com.kychan.mlog.core.dataSourceRemote.http.datasource.tmdb.TMDBDataSource
import com.kychan.mlog.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val tmdbDataSource: TMDBDataSource,
    private val movieDao: MovieDao,
): HomeRepository {
    override fun getPopularMoviesWithCategory(watchProviders: WatchProviders): Flow<List<Movie>> {
        return movieDao.getPopularMoviesWithCategory(watchProviders).map { it.map(MovieEntity::toDomain) }
    }

    override suspend fun updateMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ) {
        movieDao.upsertMovies(tmdbDataSource.getMoviePopular(
            page,
            language,
            watchRegion
        ).toEntity(page = page, watchProviders = WatchProviders.None))
    }

    override suspend fun updateMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProviders: WatchProviders
    ) {
        movieDao.upsertMovies(tmdbDataSource.getMoviePopularWithProvider(
            page,
            language,
            watchRegion,
            withWatchProviders
        ).toEntity(page = page, watchProviders = withWatchProviders))
    }
}