package com.kychan.mlog.core.dataSourceLocal.room

import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SearchDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SyncLogDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.TagDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.WatchProviderDao
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSourceImpl
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MovieRoomDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providesRoomDataSource(
        movieDao: MovieDao,
        searchDao: SearchDao,
        tagDao: TagDao,
        watchProviderDao: WatchProviderDao,
        syncLogDao: SyncLogDao,
    ): MovieRoomDataSource = MovieRoomDataSourceImpl(
        movieDao = movieDao,
        searchDao = searchDao,
        tagDao = tagDao,
        syncLogDao = syncLogDao,
        watchProviderDao = watchProviderDao
    )

    @Provides
    @Singleton
    fun providesMyMovieRoomDataSource(myMovieDao: MyMovieDao): MyMovieRoomDataSource = MyMovieRoomDataSourceImpl(myMovieDao)
}