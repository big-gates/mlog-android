package com.kychan.mlog.core.dataSourceLocal.room.datasource

import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MyMovieRoomDataSource {
    fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>>
}