package com.kychan.mlog.core.dataSourceLocal.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kychan.mlog.core.dataSourceLocal.room.converter.WatchProvidersConverters
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.*

@Database(
    entities = [
        MovieEntity::class,
        MyMovieEntity::class,
        RatedEntity::class,
        WantToWatchesEntity::class,
        TagsEntity::class
    ],
    version = 1,
    autoMigrations = [],
    exportSchema = true,
)
@TypeConverters(
    WatchProvidersConverters::class
)
abstract class MlogDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}