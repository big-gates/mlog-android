package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.data.mapper.toEntity
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.datasource.LocalDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.toDomain
import com.kychan.mlog.core.dataSourceRemote.http.datasource.RemoteDataSource
import com.kychan.mlog.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): HomeRepository {
    override fun getPopularMoviesWithCategory(watchProviders: WatchProviders): Flow<List<Movie>> {
        return localDataSource.getPopularMoviesWithCategory(watchProviders).map { it.map(MovieEntity::toDomain) }
    }

    override suspend fun updateMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ) {
        localDataSource.upsertMovies(remoteDataSource.getMoviePopular(
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
        localDataSource.upsertMovies(remoteDataSource.getMoviePopularWithProvider(
            page,
            language,
            watchRegion,
            withWatchProviders
        ).toEntity(page = page, watchProviders = withWatchProviders))
    }
}