package com.kychan.mlog.core.data.di

import com.kychan.mlog.core.data.repository.*
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MovieRoomDataSource
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
        movieRoomDataSource: MovieRoomDataSource
    ): HomeRepository = HomeRepositoryImpl(tmdbDataSource, movieRoomDataSource)

    @Provides
    @Singleton
    fun providesMyPageRepository(
        myMovieRoomDataSource: MyMovieRoomDataSource
    ): MyPageRepository = MyPageRepositoryImpl(myMovieRoomDataSource)

    @Provides
    @Singleton
    fun providesSearchRepository(
        tmdbDataSource: TMDBDataSource,
        movieRoomDataSource: MovieRoomDataSource
    ): SearchRepository = SearchRepositoryImpl(tmdbDataSource, movieRoomDataSource)

    @Provides
    @Singleton
    fun providesMovieDetailRepository(
        tmdbDataSource: TMDBDataSource,
        myMovieRoomDataSource: MyMovieRoomDataSource,
    ): MovieDetailRepository = MovieDetailRepositoryImpl(tmdbDataSource, myMovieRoomDataSource)
}