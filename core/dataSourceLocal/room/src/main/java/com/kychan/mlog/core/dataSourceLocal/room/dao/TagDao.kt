package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.kychan.mlog.core.dataSourceLocal.room.model.GenresEntity

@Dao
abstract class TagDao {

    @Upsert
    abstract suspend fun upsertTags(entities: List<GenresEntity>)
}