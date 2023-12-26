package com.kychan.mlog.core.data.repository

import androidx.paging.*
import com.kychan.mlog.core.data.mediator.MLogMovieMediator
import com.kychan.mlog.core.data.mediator.NetflixMovieMediator
import com.kychan.mlog.core.data.mediator.WatchaMovieMediator
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieVO
import com.kychan.mlog.core.dataSourceLocal.room.model.toDomain
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HomeRepositoryImpl @Inject constructor(
    private val tmdbDataSource: TMDBDataSource,
    private val movieRoomDataSource: MovieRoomDataSource
): HomeRepository {
    override fun getMlogMovie(): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(20),
            remoteMediator = MLogMovieMediator(
                movieRoomDataSource = movieRoomDataSource,
                tmdbDataSource = tmdbDataSource,
            )
        ) {
            movieRoomDataSource.getMovie(WatchProvider.MLOG_ID)
        }.flow.map { paging ->
            paging.map(MovieVO::toDomain)
        }
    }

    override fun getNetflixMovie(): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(20),
            remoteMediator = NetflixMovieMediator(
                movieRoomDataSource = movieRoomDataSource,
                tmdbDataSource = tmdbDataSource,
            )
        ) {
            movieRoomDataSource.getMovie(WatchProvider.NETFLIX_ID)
        }.flow.map { paging ->
            paging.map(MovieVO::toDomain)
        }
    }

    override fun getWatchaMovie(): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(20),
            remoteMediator = WatchaMovieMediator(
                movieRoomDataSource = movieRoomDataSource,
                tmdbDataSource = tmdbDataSource,
            )
        ) {
            movieRoomDataSource.getMovie(WatchProvider.WATCHA_ID)
        }.flow.map { paging ->
            paging.map(MovieVO::toDomain)
        }
    }

}