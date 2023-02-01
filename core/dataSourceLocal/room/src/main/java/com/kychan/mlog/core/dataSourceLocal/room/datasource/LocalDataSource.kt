package com.kychan.mlog.core.dataSourceLocal.room.datasource

import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.model.WatchProvider
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getPopularMoviesWithCategory(watchProvider: WatchProvider): Flow<List<MovieEntity>>
    fun upsertMovies(entities: List<MovieEntity>)

}