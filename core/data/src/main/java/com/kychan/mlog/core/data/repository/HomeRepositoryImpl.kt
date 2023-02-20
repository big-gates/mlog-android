package com.kychan.mlog.core.data.repository

import androidx.paging.*
import com.kychan.mlog.core.data.mediator.MLogMovieMediator
import com.kychan.mlog.core.data.mediator.NetflixMovieMediator
import com.kychan.mlog.core.data.mediator.WatchaMovieMediator
import com.kychan.mlog.core.dataSourceLocal.room.datasource.RoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.MlogMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.NetflixMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WatchaMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.toDomain
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HomeRepositoryImpl @Inject constructor(
    private val tmdbDataSource: TMDBDataSource,
    private val roomDataSource: RoomDataSource
): HomeRepository {
    override fun getMlogMovie(): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(20),
            remoteMediator = MLogMovieMediator(
                roomDataSource = roomDataSource,
                tmdbDataSource = tmdbDataSource,
            )
        ) {
            roomDataSource.getMLogMovie()
        }.flow.map { paging ->
            paging.map(MlogMovieEntity::toDomain)
        }
    }

    override fun getNetflixMovie(): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(20),
            remoteMediator = NetflixMovieMediator(
                roomDataSource = roomDataSource,
                tmdbDataSource = tmdbDataSource,
            )
        ) {
            roomDataSource.getNetflixMovie()
        }.flow.map { paging ->
            paging.map(NetflixMovieEntity::toDomain)
        }
    }

    override fun getWatchaMovie(): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(20),
            remoteMediator = WatchaMovieMediator(
                roomDataSource = roomDataSource,
                tmdbDataSource = tmdbDataSource,
            )
        ) {
            roomDataSource.getWatchaMovie()
        }.flow.map { paging ->
            paging.map(WatchaMovieEntity::toDomain)
        }
    }

}