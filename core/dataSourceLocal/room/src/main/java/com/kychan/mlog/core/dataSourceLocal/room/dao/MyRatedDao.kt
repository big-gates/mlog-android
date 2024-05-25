package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.*
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface MyRatedDao {
    @Query("""
            SELECT *
            FROM my_movie AS m
            INNER JOIN rated AS r ON m.id = r.my_movie_id
        """
    )
    abstract fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>>

    @Upsert
    abstract suspend fun upsertRatedMovie(ratedEntity: RatedEntity)

    @Delete
    abstract suspend fun deleteRatedMovie(ratedEntity: RatedEntity)

    @Query("SELECT * FROM rated AS r WHERE r.my_movie_id = (:id)")
    abstract suspend fun existToMyRatedMovie(id: Int) : RatedEntity?

}