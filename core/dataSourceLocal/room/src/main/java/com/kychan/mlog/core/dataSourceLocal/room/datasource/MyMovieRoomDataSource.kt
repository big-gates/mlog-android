package com.kychan.mlog.core.dataSourceLocal.room.datasource

import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow

interface MyMovieRoomDataSource {
    fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>>

    fun getMyWantToWatchMovies(): Flow<List<MyWantToWatchMovieVO>>

    suspend fun updateMyRatedMovie(myMovieEntity: MyMovieEntity, ratedEntity: RatedEntity)

    suspend fun insertMyWantMovie(myMovieEntity: MyMovieEntity, wantToWatchesEntity: WantToWatchesEntity)

    suspend fun deleteMyWantMovie(myMovieEntity: MyMovieEntity, wantToWatchesEntity: WantToWatchesEntity)

    suspend fun deleteMyRatedMovie(myMovieEntity: MyMovieEntity, ratedEntity: RatedEntity)

    suspend fun existToMyRatedMovie(id: Int): RatedEntity?

    suspend fun existToMyWantMovie(id: Int): WantToWatchesEntity?

    fun getMyMovieRatedAndWanted(id: Int): Flow<MyMovieRatedAndWantedVO>
}