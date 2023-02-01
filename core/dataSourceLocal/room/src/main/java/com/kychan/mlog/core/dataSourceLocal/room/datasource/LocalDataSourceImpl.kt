package com.kychan.mlog.core.dataSourceLocal.room.datasource

import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.model.WatchProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
): LocalDataSource {
    override fun getPopularMoviesWithCategory(watchProvider: WatchProvider): Flow<List<MovieEntity>> {
        return movieDao.getPopularMoviesWithCategory(watchProvider)
    }

    override fun upsertMovies(entities: List<MovieEntity>) {
        movieDao.upsertMovies(entities)
    }
}