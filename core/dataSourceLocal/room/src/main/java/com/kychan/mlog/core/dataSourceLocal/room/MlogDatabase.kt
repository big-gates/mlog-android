package com.kychan.mlog.core.dataSourceLocal.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kychan.mlog.core.dataSourceLocal.room.converter.SyncLogTypeConverter
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyGenresDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyRatedDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyWantToWatchDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SearchDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SyncLogDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.TagDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.WatchProviderDao
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
        MyGenresEntity::class,
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
    abstract fun searchDao(): SearchDao
    abstract fun tagDao(): TagDao
    abstract fun syncLogDao(): SyncLogDao
    abstract fun watchProviderDao(): WatchProviderDao
    abstract fun myMovieDao(): MyMovieDao
    abstract fun myRatedDao(): MyRatedDao
    abstract fun myWantToWatchDao(): MyWantToWatchDao
    abstract fun myGenresDao(): MyGenresDao

    companion object {
        fun create(context: Context, useInMemory: Boolean): MlogDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, MlogDatabase::class.java)
            } else {
                Room.databaseBuilder(
                    context,
                    MlogDatabase::class.java,
                    "movie_database"
                ).addCallback(object: Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.execSQL("INSERT INTO sync_log (id, type, next_key, created_at, updated_at) VALUES (null, 'Netflix_Movie', 1, date(), date());")
                        db.execSQL("INSERT INTO sync_log (id, type, next_key, created_at, updated_at) VALUES (null, 'Watcha_Movie', 1, date(), date());")
                        db.execSQL("INSERT INTO sync_log (id, type, next_key, created_at, updated_at) VALUES (null, 'Mlog_Movie', 1, date(), date());")
                    }
                })
            }
            return databaseBuilder
                .build()
        }
    }
}