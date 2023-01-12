package com.kychan.mlog.core.data.di

import com.kychan.mlog.core.data.repository.HomeRepositoryImpl
import com.kychan.mlog.core.dataSourceRemote.http.datasource.tmdb.TMDBDataSource
import com.kychan.mlog.core.data.repository.HomeRepository
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
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
    fun providesHomeRepository(tmdbDataSource: TMDBDataSource, movieDao: MovieDao): HomeRepository = HomeRepositoryImpl(tmdbDataSource, movieDao)
}