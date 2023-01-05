package com.kychan.mlog.core.dataSourceLocal.room

import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesMovieDao(
        database: MlogDatabase
    ): MovieDao = database.movieDao()
}