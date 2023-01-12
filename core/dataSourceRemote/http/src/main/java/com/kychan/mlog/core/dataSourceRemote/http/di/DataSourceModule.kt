package com.kychan.mlog.core.dataSourceRemote.http.di

import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.mlog.core.dataSourceRemote.http.datasource.RemoteDataSource
import com.kychan.mlog.core.dataSourceRemote.http.datasource.RemoteDataSourceImpl
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
    fun providesRemoteDataSource(tmdbApi: RetrofitTMDBApi): RemoteDataSource = RemoteDataSourceImpl(tmdbApi)
}