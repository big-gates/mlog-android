package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.*
import com.kychan.mlog.core.dataSourceLocal.room.model.*

@Dao
abstract class MyGenresDao {

    @Upsert
    abstract suspend fun upsertTags(entities: List<MyGenresEntity>)
}