package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieRatedAndWantedVO
import com.kychan.mlog.core.dataSourceLocal.room.model.toDomain
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.dataSourceRemote.http.model.toDomain
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.MovieDetail
import com.kychan.mlog.core.model.MyMovieRatedAndWanted
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val tmdbDataSource: TMDBDataSource,
    private val myMovieRoomDataSource: MyMovieRoomDataSource,
) : MovieDetailRepository {
    override suspend fun getMovieDetail(
        movieId: Int,
        language: Language,
        appendToResponse: String,
    ): MovieDetail {
        return tmdbDataSource.getMovieDetail(movieId, language, appendToResponse).toDomain()
    }

    override fun getMyMovieRatedAndWanted(id: Int): Flow<MyMovieRatedAndWanted> {
        return myMovieRoomDataSource.getMyMovieRatedAndWanted(id).map(MyMovieRatedAndWantedVO::toDomain)
    }
}