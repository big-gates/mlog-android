package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.model.*
import kotlinx.coroutines.flow.Flow

interface MyPageRepository {
    fun getMyRatedMovies(): Flow<List<MyRatedMovies>>

    fun getMyWantToWatchMovies(): Flow<List<MyWantToWatchMovie>>

    suspend fun updateMyRatedMovie(myMovie: MyMovie, rated: Rated, myGenres: List<Int>)

    suspend fun insertMyWantMovie(myMovie: MyMovie, wantToWatch: WantToWatch, myGenres: List<Int>)

    suspend fun deleteMyWantMovie(myMovie: MyMovie, wantToWatch: WantToWatch)

    suspend fun deleteMyRatedMovie(myMovie: MyMovie, rated: Rated)

    suspend fun existToMyRatedMovie(id: Int): Rated?

    suspend fun existToMyWantMovie(id: Int): WantToWatch?
}