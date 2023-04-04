package com.kychan.mlog.core.dataSourceLocal.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kychan.mlog.core.dataSourceLocal.room.converter.SyncLogTypeConverter
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MlogMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.NetflixMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WatchaMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.*

@Database(
    entities = [
        MyMovieEntity::class,
        RatedEntity::class,
        WantToWatchesEntity::class,
        TagsEntity::class,
        MlogMovieEntity::class,
        NetflixMovieEntity::class,
        WatchaMovieEntity::class,
        SyncLogEntity::class,
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
}