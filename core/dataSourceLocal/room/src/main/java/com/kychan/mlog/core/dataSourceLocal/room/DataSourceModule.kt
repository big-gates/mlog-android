package com.kychan.mlog.core.dataSourceLocal.room

import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyGenresDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyRatedDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyWantToWatchDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SearchDao
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSourceImpl
import com.kychan.mlog.core.dataSourceLocal.room.datasource.RoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.datasource.RoomDataSourceImpl
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
    ): RoomDataSource = RoomDataSourceImpl(movieDao, searchDao)

    @Provides
    @Singleton
    fun providesMyMovieRoomDataSource(
        myMovieDao: MyMovieDao,
        myRatedDao: MyRatedDao,
        myWantToWatchDao: MyWantToWatchDao,
        myGenresDao: MyGenresDao,
    ): MyMovieRoomDataSource = MyMovieRoomDataSourceImpl(myMovieDao, myRatedDao, myWantToWatchDao, myGenresDao)
}