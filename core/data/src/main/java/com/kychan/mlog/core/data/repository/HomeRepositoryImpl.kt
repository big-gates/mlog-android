package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.data.mapper.toEntity
import com.kychan.mlog.core.dataSourceLocal.room.datasource.RoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.toDomain
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val tmdbDataSource: TMDBDataSource,
    private val roomDataSource: RoomDataSource
): HomeRepository {
    override fun getPopularMoviesWithCategory(watchProvider: WatchProvider): Flow<List<Movie>> {
        return roomDataSource.getPopularMoviesWithCategory(watchProvider).map { it.map(MovieEntity::toDomain) }
    }

    override suspend fun updateMoviePopular(
        page: Int,
        language: Language,
        watchRegion: WatchRegion
    ) {
        roomDataSource.upsertMovies(tmdbDataSource.getMoviePopular(
            page,
            language,
            watchRegion
        ).toEntity(page = page, watchProvider = WatchProvider.None))
    }

    override suspend fun updateMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProvider: WatchProvider
    ) {
        roomDataSource.upsertMovies(tmdbDataSource.getMoviePopularWithProvider(
            page,
            language,
            watchRegion,
            withWatchProvider
        ).toEntity(page = page, watchProvider = withWatchProvider))
    }
}