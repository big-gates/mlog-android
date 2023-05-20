package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.MovieDetail

interface MovieDetailRepository {
    suspend fun getMovieDetail(
        movieId: Int,
        language: Language,
        appendToResponse: String,
    ): MovieDetail
}