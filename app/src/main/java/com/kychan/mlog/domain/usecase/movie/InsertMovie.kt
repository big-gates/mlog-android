package com.kychan.mlog.domain.usecase.movie

import com.kychan.mlog.data.local.model.MovieEntity
import com.kychan.mlog.domain.repository.MovieRepository
import javax.inject.Inject

class InsertMovie @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(movieEntity: MovieEntity) {
        return movieRepository.insertMovie(movieEntity)
    }
}
