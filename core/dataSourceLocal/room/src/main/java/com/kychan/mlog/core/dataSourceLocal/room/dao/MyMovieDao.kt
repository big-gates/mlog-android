package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.*
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class MyMovieDao {
    @Query("""
            SELECT r.my_movie_id
            , m.adult
            , m.backdrop_path
            , m.original_title
            , m.poster_path
            , m.title
            , m.vote_average
            , m.watch_providers
            , m.rank
            , r.rated
            , r.comment
            , r.created_at
            FROM my_movie AS m
            INNER JOIN rated AS r ON m.id = r.my_movie_id
        """
    )
    abstract fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>>

    @Query("SELECT * FROM my_movie AS m INNER JOIN want_to_watches AS w ON m.id = w.my_movie_id")
    abstract fun getMyWantToWatchMovies(): Flow<List<MyMovieEntity>>

    @Transaction
    open suspend fun updateMyRatedMovie(myMovieEntity: MyMovieEntity, ratedEntity: RatedEntity) {
        upsertMyMovie(myMovieEntity)
        if (ratedEntity.rated < 0f) { // rate : 사용자가 0 입력 시 -1로 저장 후 삭제 로직 실행
            deleteMyRatedMovie(myMovieEntity, ratedEntity)
        } else (
            existToMyRatedMovie(myMovieEntity.id)?.let {
                upsertRatedMovie(ratedEntity.copy(createdAt = it.createdAt))
            } ?: upsertRatedMovie(ratedEntity)
        )
    }

    @Transaction
    open suspend fun insertMyWantMovie(myMovieEntity: MyMovieEntity, wantToWatchesEntity: WantToWatchesEntity) {
        upsertMyMovie(myMovieEntity)
        insertWantMovie(wantToWatchesEntity)
    }

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

    @Upsert
    abstract suspend fun upsertRatedMovie(ratedEntity: RatedEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertWantMovie(wantToWatchesEntity: WantToWatchesEntity)

    @Delete
    abstract suspend fun deleteMyMovie(myMovieEntity: MyMovieEntity)

    @Delete
    abstract suspend fun deleteWantMovie(wantToWatchesEntity: WantToWatchesEntity)

    @Delete
    abstract suspend fun deleteRatedMovie(ratedEntity: RatedEntity)

    @Query("SELECT * FROM rated AS r WHERE r.my_movie_id = (:id)")
    abstract suspend fun existToMyRatedMovie(id: Int) : RatedEntity?

    @Query("SELECT * FROM want_to_watches AS w WHERE w.my_movie_id = (:id)")
    abstract suspend fun existToMyWantMovie(id: Int) : WantToWatchesEntity?

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