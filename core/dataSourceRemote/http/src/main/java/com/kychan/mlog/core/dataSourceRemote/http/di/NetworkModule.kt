package com.kychan.mlog.core.dataSourceRemote.http.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kychan.mlog.core.dataSourceRemote.http.BuildConfig
import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


private const val THE_MOVIE_DB_BASE_URL = BuildConfig.THE_MOVIE_DB_API_URL
private const val THE_MOVIE_DB_API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesTMDBApi(networkJson: Json): RetrofitTMDBApi = Retrofit.Builder()
        .baseUrl(THE_MOVIE_DB_BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = when(BuildConfig.BUILD_TYPE){
                            "debug" -> HttpLoggingInterceptor.Level.BODY
                            else -> HttpLoggingInterceptor.Level.NONE
                        }
                    }
                )
                .addInterceptor(Interceptor {
                    val url = it.request().url.newBuilder().addQueryParameter("api_key", THE_MOVIE_DB_API_KEY).build()
                    val request = it.request().newBuilder().url(url).build()
                    it.proceed(request)
                })
                .build()
        )
        .addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitTMDBApi::class.java)

}