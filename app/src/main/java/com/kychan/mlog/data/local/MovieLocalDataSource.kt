package com.kychan.mlog.data.local

import androidx.paging.DataSource
import com.kychan.mlog.data.local.dao.MovieDao
import com.kychan.mlog.data.local.model.MovieEntity
import javax.inject.Inject

interface MovieLocalDataSource {
    fun getMovieAll(): DataSource.Factory<Int, MovieEntity>

    fun insertMovie(movieEntity: MovieEntity)

    fun deleteMovie(link: String)

    fun updateMovie(evaluation: Float, link: String)
}

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieLocalDataSource {
    override fun getMovieAll(): DataSource.Factory<Int, MovieEntity> {
        return movieDao.getMovieAll()
    }

    override fun insertMovie(movieEntity: MovieEntity) {
        movieDao.insert(movieEntity)
    }

    override fun deleteMovie(link: String) {
        movieDao.delete(link)
    }

    override fun updateMovie(evaluation: Float, link: String) {
        movieDao.update(evaluation, link)
    }
}
