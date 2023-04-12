package com.kychan.mlog.core.dataSourceLocal.room.datasource

import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import com.kychan.mlog.core.dataSourceLocal.room.model.WantToWatchesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyMovieRoomDataSourceImpl @Inject constructor(
    private val myMovieDao: MyMovieDao,
): MyMovieRoomDataSource {

    override fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>> {
        return myMovieDao.getMyRatedMovies()
    }

    override suspend fun insertMyWantMovie(myMovieEntity: MyMovieEntity, wantToWatchesEntity: WantToWatchesEntity) {
        return myMovieDao.insertMyWantMovie(myMovieEntity, wantToWatchesEntity)
    }
}