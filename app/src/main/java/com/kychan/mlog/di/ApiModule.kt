package com.kychan.mlog.di

import com.kychan.mlog.data.remote.NaverApi
import com.kychan.mlog.data.remote.TheMovieDBApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val NAVER_URL = "https://openapi.naver.com/"
    private const val THE_MOVIE_DB_URL = "https://api.themoviedb.org/"

    @Singleton
    @Provides
    fun provideNaverApi(): NaverApi {
        return Retrofit.Builder()
            .baseUrl(NAVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NaverApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTheMovieDBApi(): TheMovieDBApi {
        return Retrofit.Builder()
            .baseUrl(THE_MOVIE_DB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBApi::class.java)
    }
}
