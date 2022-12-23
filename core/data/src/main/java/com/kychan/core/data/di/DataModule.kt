package com.kychan.core.data.di

import com.kychan.core.data.repository.HomeRepositoryImpl
import com.kychan.core.dataSourceRemote.http.datasource.tmdb.TMDBDataSource
import com.kychan.core.data.repository.HomeRepository
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
    fun providesHomeRepository(tmdbDataSource: TMDBDataSource): HomeRepository = HomeRepositoryImpl(tmdbDataSource)
}