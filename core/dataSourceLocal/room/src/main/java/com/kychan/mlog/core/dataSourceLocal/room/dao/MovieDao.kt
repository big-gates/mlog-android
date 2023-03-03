package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.*
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
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
    fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>>

}