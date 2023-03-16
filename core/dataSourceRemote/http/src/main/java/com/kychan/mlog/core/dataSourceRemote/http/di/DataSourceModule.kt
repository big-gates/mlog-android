package com.kychan.mlog.core.dataSourceRemote.http.di

import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSourceImpl
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
    fun providesTMDBDataSource(tmdbApi: RetrofitTMDBApi): TMDBDataSource = TMDBDataSourceImpl(tmdbApi)
}