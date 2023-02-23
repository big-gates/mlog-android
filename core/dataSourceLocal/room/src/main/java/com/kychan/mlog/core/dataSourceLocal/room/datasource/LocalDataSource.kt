package com.kychan.mlog.core.dataSourceLocal.room.datasource

import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import com.kychan.mlog.core.model.WatchProviders
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getPopularMoviesWithCategory(watchProviders: WatchProviders): Flow<List<MovieEntity>>
    fun upsertMovies(entities: List<MovieEntity>)
    fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>>
}