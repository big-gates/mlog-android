package com.kychan.mlog.domain.usecase.movie

import com.kychan.mlog.domain.repository.MovieRepository
import javax.inject.Inject

class InvalidateDataSource @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke() {
        movieRepository.invalidateDataSource()
    }
}
