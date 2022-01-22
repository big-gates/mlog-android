package com.kychan.mlog.di

import com.kychan.mlog.data.remote.NaverApi
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

    @Singleton
    @Provides
    fun provideNaverApi(): NaverApi {
        return Retrofit.Builder()
            .baseUrl(NAVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NaverApi::class.java)
    }
}
