package com.kychan.mlog.core.dataSourceLocal.room

import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyGenresDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyRatedDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyWantToWatchDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SearchDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SyncLogDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.TagDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.WatchProviderDao
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
    fun providesMyRatedDao(
        database: MlogDatabase
    ): MyRatedDao = database.myRatedDao()

    @Provides
    fun providesMyWantToWatchDao(
        database: MlogDatabase
    ): MyWantToWatchDao = database.myWantToWatchDao()

    @Provides
    fun providesMyGenresDao(
        database: MlogDatabase
    ): MyGenresDao = database.myGenresDao()

    @Provides
    fun providesSearchDao(
        database: MlogDatabase
    ): SearchDao = database.searchDao()

    @Provides
    fun providesTagDao(
        database: MlogDatabase
    ): TagDao = database.tagDao()

    @Provides
    fun providesSyncLogDao(
        database: MlogDatabase
    ): SyncLogDao = database.syncLogDao()

    @Provides
    fun providesWatchProviderDao(
        database: MlogDatabase
    ): WatchProviderDao = database.watchProviderDao()
}