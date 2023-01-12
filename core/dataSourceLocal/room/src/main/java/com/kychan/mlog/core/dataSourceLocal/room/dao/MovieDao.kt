package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.WatchProviders
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query(
        value = """
            SELECT * FROM movie
            WHERE watchProviders = :watchProviders
            ORDER BY rank ASC
        """
    )
    fun getPopularMoviesWithCategory(watchProviders: WatchProviders): Flow<List<MovieEntity>>

    @Upsert
    fun upsertMovies(entities: List<MovieEntity>)
}