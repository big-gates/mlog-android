package com.kychan.mlog.core.dataSourceLocal.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kychan.mlog.core.dataSourceLocal.room.converter.WatchProvidersConverters
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WatchProviderEntity

@Database(
    entities = [
        MovieEntity::class,
        WatchProviderEntity::class
    ],
    version = 1,
    autoMigrations = [],
    exportSchema = true,
)
@TypeConverters(
    WatchProvidersConverters::class
)
abstract class MlogDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}