package com.kychan.mlog.domain.usecase.movie

import com.kychan.mlog.domain.repository.MovieRepository
import javax.inject.Inject

class SetSearchKeyword @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(keyword: String) {
        movieRepository.setSearchKeyword(keyword)
    }
}
