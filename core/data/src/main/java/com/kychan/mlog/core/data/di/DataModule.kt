package com.kychan.mlog.core.data.di

import com.kychan.mlog.core.data.repository.HomeRepositoryImpl
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.data.repository.HomeRepository
import com.kychan.mlog.core.data.repository.MyPageRepository
import com.kychan.mlog.core.data.repository.MyPageRepositoryImpl
import com.kychan.mlog.core.data.repository.SearchRepository
import com.kychan.mlog.core.data.repository.SearchRepositoryImpl
import com.kychan.mlog.core.dataSourceLocal.room.datasource.RoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesHomeRepository(
        tmdbDataSource: TMDBDataSource,
        roomDataSource: RoomDataSource
    ): HomeRepository = HomeRepositoryImpl(tmdbDataSource, roomDataSource)

    @Provides
    @Singleton
    fun providesMyPageRepository(
        roomDataSource: RoomDataSource
    ): MyPageRepository = MyPageRepositoryImpl(roomDataSource)

    @Provides
    @Singleton
    fun providesSearchRepository(
        tmdbDataSource: TMDBDataSource,
        roomDataSource: RoomDataSource
    ): SearchRepository = SearchRepositoryImpl(tmdbDataSource, roomDataSource)
}