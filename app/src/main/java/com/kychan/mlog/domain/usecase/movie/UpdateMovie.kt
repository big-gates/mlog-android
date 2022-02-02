package com.kychan.mlog.domain.usecase.movie

import com.kychan.mlog.domain.repository.MovieRepository
import javax.inject.Inject

class UpdateMovie @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(evaluation: Float, link: String) {
        return movieRepository.updateMovie(evaluation, link)
    }
}
