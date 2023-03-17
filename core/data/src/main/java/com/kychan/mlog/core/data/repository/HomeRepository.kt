package com.kychan.mlog.core.data.repository

import androidx.paging.PagingData
import com.kychan.mlog.core.model.*
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getMlogMovie(): Flow<PagingData<Movie>>

    fun getNetflixMovie(): Flow<PagingData<Movie>>

    fun getWatchaMovie(): Flow<PagingData<Movie>>
}