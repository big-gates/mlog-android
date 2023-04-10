package com.kychan.mlog.core.dataSourceLocal.room

import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SearchDao
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

    @Provides
    fun providesMyMovieDao(
        database: MlogDatabase
    ): MyMovieDao = database.myMovieDao()

    @Provides
    fun providesSearchDao(
        database: MlogDatabase
    ): SearchDao = database.searchDao()
}