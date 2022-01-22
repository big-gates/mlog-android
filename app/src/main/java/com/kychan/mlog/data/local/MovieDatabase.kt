package com.kychan.mlog.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kychan.mlog.data.local.dao.MovieDao
import com.kychan.mlog.data.local.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}