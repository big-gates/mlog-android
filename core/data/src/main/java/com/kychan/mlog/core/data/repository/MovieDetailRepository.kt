package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.MovieDetail
import com.kychan.mlog.core.model.MyMovieRatedAndWanted
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    suspend fun getMovieDetail(
        movieId: Int,
        language: Language,
        appendToResponse: String,
    ): MovieDetail

    fun getMyMovieRatedAndWanted(id: Int): Flow<MyMovieRatedAndWanted>
}