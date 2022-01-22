package com.kychan.mlog.data.local

import androidx.paging.DataSource
import com.kychan.mlog.data.local.dao.MovieDao
import com.kychan.mlog.data.local.model.MovieEntity
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {
    fun getMovieAll(): DataSource.Factory<Int, MovieEntity> {
        return movieDao.getMovieAll()
    }

    fun insertMovie(movieEntity: MovieEntity) {
        movieDao.insert(movieEntity)
    }

    fun deleteMovie(link: String) {
        movieDao.delete(link)
    }

    fun updateMovie(evaluation: Float, link: String) {
        movieDao.update(evaluation, link)
    }
}
