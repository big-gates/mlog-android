package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.dataSourceRemote.http.model.toDomain
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.MovieDetail
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val tmdbDataSource: TMDBDataSource
) : MovieDetailRepository {
    override suspend fun getMovieDetail(
        movieId: Int,
        language: Language,
        appendToResponse: String,
    ): MovieDetail {
        return tmdbDataSource.getMovieDetail(movieId, language, appendToResponse).toDomain()
    }
}