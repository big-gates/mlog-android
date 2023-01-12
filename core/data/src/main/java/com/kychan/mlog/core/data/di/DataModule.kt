package com.kychan.mlog.core.data.di

import com.kychan.mlog.core.data.repository.HomeRepositoryImpl
import com.kychan.mlog.core.dataSourceRemote.http.datasource.RemoteDataSource
import com.kychan.mlog.core.data.repository.HomeRepository
import com.kychan.mlog.core.dataSourceLocal.room.datasource.LocalDataSource
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
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): HomeRepository = HomeRepositoryImpl(remoteDataSource, localDataSource)
}