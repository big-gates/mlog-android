package com.kychan.mlog.core.dataSourceLocal.room.datasource

import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import com.kychan.mlog.core.dataSourceLocal.room.model.RatedEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WantToWatchesEntity
import kotlinx.coroutines.flow.Flow

interface MyMovieRoomDataSource {
    fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>>

    fun getMyWantToWatchMovies(): Flow<List<MyMovieEntity>>

    suspend fun insertMyRatedMovie(myMovieEntity: MyMovieEntity, ratedEntity: RatedEntity)

    suspend fun insertMyWantMovie(myMovieEntity: MyMovieEntity, wantToWatchesEntity: WantToWatchesEntity)

    suspend fun existToMyWantMovie(id: Int): Int
}