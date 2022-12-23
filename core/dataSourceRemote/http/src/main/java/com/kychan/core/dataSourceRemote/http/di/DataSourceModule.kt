package com.kychan.core.dataSourceRemote.http.di

import com.kychan.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.core.dataSourceRemote.http.datasource.tmdb.TMDBDataSource
import com.kychan.core.dataSourceRemote.http.datasource.tmdb.TMDBDataSourceImpl
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