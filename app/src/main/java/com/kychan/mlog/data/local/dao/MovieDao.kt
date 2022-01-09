package com.kychan.mlog.data.local.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kychan.mlog.data.local.model.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getMovieAll(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT link FROM movie_table")
    fun getMovieId(): List<String>

    @Insert
    fun insert(movieEntity: MovieEntity)

    @Insert
    fun insertAll(movieEntity: List<MovieEntity>)

    @Query("DELETE FROM movie_table WHERE link = (:link)")
    fun delete(link: String)

    @Query("UPDATE movie_table SET evaluation= (:evaluation) WHERE link = (:link)")
    fun update(evaluation: Float, link: String)

}