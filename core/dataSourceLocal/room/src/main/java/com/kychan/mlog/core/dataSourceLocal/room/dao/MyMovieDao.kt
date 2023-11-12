package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.*
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class MyMovieDao {

    @Query("SELECT * FROM my_movie")
    abstract fun getMyMovies(): Flow<List<MyMovieEntity>>

    @Upsert
    abstract suspend fun upsertMyMovie(myMovieEntity: MyMovieEntity)

    @Delete
    abstract suspend fun deleteMyMovie(myMovieEntity: MyMovieEntity)

    @Query(
        """
        SELECT r.rated
        ,r.comment
        ,w.my_movie_id
            FROM my_movie AS m
                LEFT JOIN rated AS r
                    ON m.id = r.my_movie_id
                LEFT JOIN want_to_watches AS w
                    ON m.id = w.my_movie_id
            WHERE m.id = (:id)
        """
    )
    abstract fun getMyMovieRatedAndWanted(id: Int): Flow<MyMovieRatedAndWantedVO>
}