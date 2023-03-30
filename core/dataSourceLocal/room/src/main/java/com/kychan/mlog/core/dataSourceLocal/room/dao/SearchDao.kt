package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kychan.mlog.core.dataSourceLocal.room.model.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SearchDao {

    @Upsert
    abstract suspend fun upsertRecentSearch(recentSearchEntity: RecentSearchEntity)

    @Query(value = """
        SELECT * FROM recent_search
        ORDER BY created_at DESC
    """)
    abstract fun getRecentSearch(): Flow<List<RecentSearchEntity>>

    @Query(value = """
        DELETE FROM recent_search
    """)
    abstract suspend fun deleteAllRecentSearch()

    @Query(value = """
        DELETE FROM recent_search
        WHERE id = :id
    """)
    abstract suspend fun deleteRecentSearch(id: Int)

}