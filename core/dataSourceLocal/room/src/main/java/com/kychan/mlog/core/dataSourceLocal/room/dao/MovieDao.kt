package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.WatchProviders

@Dao
interface MovieDao {
    @Query(
        value = """
            SELECT * FROM movie
            WHERE watchProviders = :watchProviders
        """
    )
    fun getPopularMoviesWithCategory(watchProviders: WatchProviders): List<Movie>

    @Upsert
    fun insertMovies(entities: MovieEntity)
}