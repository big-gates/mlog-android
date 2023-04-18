package com.kychan.mlog.core.dataSourceLocal.room.datasource

import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import com.kychan.mlog.core.dataSourceLocal.room.model.RatedEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WantToWatchesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyMovieRoomDataSourceImpl @Inject constructor(
    private val myMovieDao: MyMovieDao,
): MyMovieRoomDataSource {

    override fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>> {
        return myMovieDao.getMyRatedMovies()
    }

    override fun getMyWantToWatchMovies(): Flow<List<MyMovieEntity>> {
        return myMovieDao.getMyWantToWatchMovies()
    }

    override suspend fun insertMyRatedMovie(myMovieEntity: MyMovieEntity, ratedEntity: RatedEntity) {
        return myMovieDao.insertMyRatedMovie(myMovieEntity, ratedEntity)
    }

    override suspend fun insertMyWantMovie(myMovieEntity: MyMovieEntity, wantToWatchesEntity: WantToWatchesEntity) {
        return myMovieDao.insertMyWantMovie(myMovieEntity, wantToWatchesEntity)
    }

    override suspend fun existToMyRatedMovie(id: Int): RatedEntity? {
        return myMovieDao.existToMyRatedMovie(id)
    }

    override suspend fun existToMyWantMovie(id: Int): Int {
        return myMovieDao.existToMyWantMovie(id)
    }
}