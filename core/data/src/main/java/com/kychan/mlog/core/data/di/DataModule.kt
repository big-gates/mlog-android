package com.kychan.mlog.core.data.di

import com.kychan.mlog.core.data.repository.HomeRepositoryImpl
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.data.repository.HomeRepository
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
}