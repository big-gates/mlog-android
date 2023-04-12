package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.model.*
import kotlinx.coroutines.flow.Flow

interface MyPageRepository {
    fun getMyRatedMovies(): Flow<List<MyRatedMovies>>

    suspend fun insertMyWantMovie(myMovie: MyMovie, wantToWatch: WantToWatch)
}