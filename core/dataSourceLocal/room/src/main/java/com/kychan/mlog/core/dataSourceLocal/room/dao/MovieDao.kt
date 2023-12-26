package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao {

    @Query(value = """
        SELECT * 
        FROM movie as m
        LEFT JOIN watch_provider as wp on m.id = wp.movie_id
        WHERE wp.watch_provider_id = :movieTypeId
        ORDER BY wp.rank ASC
    """)
    abstract fun getMovies(movieTypeId: Int): PagingSource<Int, MovieVO>

    @Query(value = """
        SELECT *
        FROM movie as m
    """)
    abstract fun getMovies(): Flow<List<MovieEntity>>

    @Upsert
    abstract suspend fun upsertMovies(entities: List<MovieEntity>)

    @Query(
        value = """
            DELETE FROM movie
            WHERE id in (:ids)
        """,
    )
    abstract suspend fun deleteMovies(ids: List<Int>)
}