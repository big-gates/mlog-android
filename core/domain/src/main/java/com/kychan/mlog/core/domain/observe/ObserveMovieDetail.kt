package com.kychan.mlog.core.domain.observe

import com.kychan.mlog.core.data.repository.MovieDetailRepository
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.MovieDetail
import javax.inject.Inject

class ObserveMovieDetail @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {

    suspend operator fun invoke(
        movieId: Int,
        language: Language,
        appendToResponse: String,
    ): MovieDetail {
        return movieDetailRepository.getMovieDetail(movieId, language, appendToResponse)
    }
}
