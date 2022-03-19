package com.kychan.mlog.di

import com.kychan.mlog.data.local.MovieLocalDataSource
import com.kychan.mlog.data.local.MovieLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindMovieLocalDataSource(movieLocalDataSourceImpl: MovieLocalDataSourceImpl): MovieLocalDataSource
}
