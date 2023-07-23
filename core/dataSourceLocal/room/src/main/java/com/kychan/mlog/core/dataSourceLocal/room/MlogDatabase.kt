package com.kychan.mlog.core.dataSourceLocal.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kychan.mlog.core.dataSourceLocal.room.converter.SyncLogTypeConverter
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SearchDao
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.*

@Database(
    entities = [
        MyMovieEntity::class,
        RatedEntity::class,
        WantToWatchesEntity::class,
        GenresEntity::class,
        WatchProviderEntity::class,
        SyncLogEntity::class,
        RecentSearchEntity::class,
        MovieEntity::class,
    ],
    version = 1,
    autoMigrations = [],
    exportSchema = true,
)
@TypeConverters(
    SyncLogTypeConverter::class,
)
abstract class MlogDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun myMovieDao(): MyMovieDao
    abstract fun searchDao(): SearchDao
}