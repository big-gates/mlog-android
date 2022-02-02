package com.kychan.mlog.di

import android.content.Context
import androidx.room.Room
import com.kychan.mlog.data.local.MovieDatabase
import com.kychan.mlog.data.local.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideMovieDao(appDatabase: MovieDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "movie_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
