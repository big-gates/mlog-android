package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.model.WatchProvider
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query(
        value = """
            SELECT * FROM movie AS m
            LEFT JOIN watch_provider AS wp ON m.watch_provider_id = wp.id
            WHERE watch_provider = :watchProvider
            ORDER BY rank ASC
        """
    )
    fun getPopularMoviesWithCategory(watchProvider: WatchProvider): Flow<List<MovieEntity>>

    @Upsert
    fun upsertMovies(entities: List<MovieEntity>)
}