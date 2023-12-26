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
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSourceImpl
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MovieRoomDataSourceImpl
import com.kychan.mlog.core.dataSourceLocal.room.datasource.SearchRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.datasource.SearchRoomDataSourceImpl
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
        tagDao: TagDao,
        watchProviderDao: WatchProviderDao,
        syncLogDao: SyncLogDao,
    ): MovieRoomDataSource = MovieRoomDataSourceImpl(
        movieDao = movieDao,
        tagDao = tagDao,
        syncLogDao = syncLogDao,
        watchProviderDao = watchProviderDao
    )

    @Provides
    @Singleton
    fun providesSearchDataSource(
        searchDao: SearchDao
    ): SearchRoomDataSource = SearchRoomDataSourceImpl(searchDao)

    @Provides
    @Singleton
    fun providesMyMovieRoomDataSource(
        myMovieDao: MyMovieDao,
        myRatedDao: MyRatedDao,
        myWantToWatchDao: MyWantToWatchDao,
        myGenresDao: MyGenresDao,
    ): MyMovieRoomDataSource = MyMovieRoomDataSourceImpl(myMovieDao, myRatedDao, myWantToWatchDao, myGenresDao)
}