package com.kychan.mlog.core.dataSourceLocal.room.datasource

import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import com.kychan.mlog.core.dataSourceLocal.room.model.RatedEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WantToWatchesEntity
import kotlinx.coroutines.flow.Flow

interface MyMovieRoomDataSource {
    fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>>

    fun getMyWantToWatchMovies(): Flow<List<MyMovieEntity>>

    suspend fun updateMyRatedMovie(myMovieEntity: MyMovieEntity, ratedEntity: RatedEntity)

    suspend fun insertMyWantMovie(myMovieEntity: MyMovieEntity, wantToWatchesEntity: WantToWatchesEntity)

    suspend fun deleteMyWantMovie(myMovieEntity: MyMovieEntity, wantToWatchesEntity: WantToWatchesEntity)

    suspend fun deleteMyRatedMovie(myMovieEntity: MyMovieEntity, ratedEntity: RatedEntity)

    suspend fun existToMyRatedMovie(id: Int): RatedEntity?

    suspend fun existToMyWantMovie(id: Int): WantToWatchesEntity?
}