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
            FROM my_movie AS m
            INNER JOIN rated AS r ON m.id = r.my_movie_id
        """
    )
    abstract fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>>


    @Transaction
    open suspend fun insertMyWantMovie(myMovieEntity: MyMovieEntity, wantToWatchesEntity: WantToWatchesEntity) {
        insertMyMovie(myMovieEntity)
        insertWantMovie(wantToWatchesEntity)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMyMovie(myMovieEntity: MyMovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertWantMovie(wantToWatchesEntity: WantToWatchesEntity)

}