package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kychan.mlog.core.dataSourceLocal.room.model.GenresEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TagDao {

    @Upsert
    abstract suspend fun upsertTags(entities: List<GenresEntity>)

    @Query(value = """
        SELECT *
        FROM genre as g
    """)
    abstract fun getTags(): Flow<List<GenresEntity>>
}