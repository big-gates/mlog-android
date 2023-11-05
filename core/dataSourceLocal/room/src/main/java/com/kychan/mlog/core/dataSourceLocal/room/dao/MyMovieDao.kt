package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.*
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class MyMovieDao {

    @Transaction
    open suspend fun deleteMyWantMovie(myMovieEntity: MyMovieEntity, wantToWatchesEntity: WantToWatchesEntity) {
        deleteWantMovie(wantToWatchesEntity)
        if (existToMyRatedMovie(myMovieEntity.id) == null) {
            deleteMyMovie(myMovieEntity)
        }
    }

    @Transaction
    open suspend fun deleteMyRatedMovie(myMovieEntity: MyMovieEntity, ratedEntity: RatedEntity) {
        deleteRatedMovie(ratedEntity)
        if (existToMyWantMovie(myMovieEntity.id) == null) {
            deleteMyMovie(myMovieEntity)
        }
    }

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