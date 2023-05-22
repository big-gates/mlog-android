package com.kychan.mlog.core.dataSourceLocal.room.datasource

import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.*
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

    override suspend fun updateMyRatedMovie(myMovieEntity: MyMovieEntity, ratedEntity: RatedEntity) {
        return myMovieDao.updateMyRatedMovie(myMovieEntity, ratedEntity)
    }

    override suspend fun insertMyWantMovie(myMovieEntity: MyMovieEntity, wantToWatchesEntity: WantToWatchesEntity) {
        return myMovieDao.insertMyWantMovie(myMovieEntity, wantToWatchesEntity)
    }

    override suspend fun deleteMyWantMovie(myMovieEntity: MyMovieEntity, wantToWatchesEntity: WantToWatchesEntity) {
        return myMovieDao.deleteMyWantMovie(myMovieEntity, wantToWatchesEntity)
    }

    override suspend fun deleteMyRatedMovie(myMovieEntity: MyMovieEntity, ratedEntity: RatedEntity) {
        return myMovieDao.deleteMyRatedMovie(myMovieEntity, ratedEntity)
    }

    override suspend fun existToMyRatedMovie(id: Int): RatedEntity? {
        return myMovieDao.existToMyRatedMovie(id)
    }

    override suspend fun existToMyWantMovie(id: Int): WantToWatchesEntity? {
        return myMovieDao.existToMyWantMovie(id)
    }

    override fun getMyMovieRatedAndWanted(id: Int): Flow<MyMovieRatedAndWantedVO> {
        return myMovieDao.getMyMovieRatedAndWanted(id)
    }
}