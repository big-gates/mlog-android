package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kychan.mlog.core.dataSourceLocal.room.model.WatchProviderEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WatchProviderDao {

    @Upsert
    abstract suspend fun upsertWatchProviders(entities: List<WatchProviderEntity>)

    @Query(
        value = """
            SELECT movie_id
            FROM watch_provider
            WHERE watch_provider_id = :watchProviderId
        """,
    )
    abstract fun getMovieIds(watchProviderId: Int): Flow<List<Int>>

    @Query(
        value = """
            SELECT *
            FROM watch_provider
        """,
    )
    abstract fun getWatchProviders(): Flow<List<WatchProviderEntity>>
}