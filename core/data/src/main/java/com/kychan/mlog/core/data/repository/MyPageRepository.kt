package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.dataSourceLocal.room.model.RatedEntity
import com.kychan.mlog.core.model.*
import kotlinx.coroutines.flow.Flow

interface MyPageRepository {
    fun getMyRatedMovies(): Flow<List<MyRatedMovies>>

    fun getMyWantToWatchMovies(): Flow<List<MyMovie>>

    suspend fun insertMyRatedMovie(myMovie: MyMovie, rated: Rated)

    suspend fun insertMyWantMovie(myMovie: MyMovie, wantToWatch: WantToWatch)

    suspend fun deleteMyWantMovie(myMovie: MyMovie, wantToWatch: WantToWatch)

    suspend fun existToMyRatedMovie(id: Int): Rated?

    suspend fun existToMyWantMovie(id: Int): Int
}