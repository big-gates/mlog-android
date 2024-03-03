package com.kychan.mlog.core.dataSourceLocal.room

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesMlogDatabase(
        @ApplicationContext context: Context,
    ): MlogDatabase = MlogDatabase.create(context, useInMemory = false)
}