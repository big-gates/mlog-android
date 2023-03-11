package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.dataSourceLocal.room.datasource.RoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import com.kychan.mlog.core.dataSourceLocal.room.model.toDomain
import com.kychan.mlog.core.model.MyRatedMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource
): MyPageRepository {
    override fun getMyRatedMovies(): Flow<List<MyRatedMovies>> {
        return roomDataSource.getMyRatedMovies().map { it.map(MyRatedMoviesVO::toDomain) }
    }
}