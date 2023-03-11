package com.kychan.mlog.core.dataSourceLocal.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
    ): MlogDatabase = Room.databaseBuilder(
        context,
        MlogDatabase::class.java,
        "movie_database"
    ).addCallback(object: RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            db.execSQL("INSERT INTO sync_log (id, type, next_key, created_at, updated_at) VALUES (null, 'Netflix_Movie', 1, date(), date());")
            db.execSQL("INSERT INTO sync_log (id, type, next_key, created_at, updated_at) VALUES (null, 'Watcha_Movie', 1, date(), date());")
            db.execSQL("INSERT INTO sync_log (id, type, next_key, created_at, updated_at) VALUES (null, 'Mlog_Movie', 1, date(), date());")
        }
    }).build()
}