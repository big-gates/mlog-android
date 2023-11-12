package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.*
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class MyGenresDao {

    @Query("SELECT * FROM my_genre")
    abstract fun getMyGenres(): Flow<List<MyGenresEntity>>

    @Upsert
    abstract suspend fun upsertTags(entities: List<MyGenresEntity>)
}