package com.kychan.mlog.core.dataSourceLocal.room.datasource.fake

import com.kychan.mlog.core.dataSourceLocal.room.dao.MyGenresDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyRatedDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.MyWantToWatchDao
import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.MyGenresEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieRatedAndWantedVO
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import com.kychan.mlog.core.dataSourceLocal.room.model.MyWantToWatchMovieVO
import com.kychan.mlog.core.dataSourceLocal.room.model.RatedEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WantToWatchesEntity
import kotlinx.coroutines.flow.Flow

class FakeMyMovieRoomDataSource(
    private val myMovieDao: MyMovieDao,
    private val myRatedDao: MyRatedDao,
    private val myWantToWatchDao: MyWantToWatchDao,
    private val myGenresDao: MyGenresDao,
): MyMovieRoomDataSource {
    override fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>> {
        return myRatedDao.getMyRatedMovies()
    }

    override fun getMyWantToWatchMovies(): Flow<List<MyWantToWatchMovieVO>> {
        return myWantToWatchDao.getMyWantToWatchMovies()
    }

    override suspend fun updateMyRatedMovie(
        myMovieEntity: MyMovieEntity,
        ratedEntity: RatedEntity,
        myGenres: List<Int>
    ) {
        myMovieDao.upsertMyMovie(myMovieEntity)
        myGenresDao.upsertTags(myGenres.map { MyGenresEntity(genreId = it, movieId = myMovieEntity.id) })
        if (ratedEntity.rated < 0f) { // rate : 사용자가 0 입력 시 -1로 저장 후 삭제 로직 실행
            deleteMyRatedMovie(myMovieEntity, ratedEntity)
        } else {
            val createdAt = existToMyRatedMovie(myMovieEntity.id)?.createdAt
            myRatedDao.upsertRatedMovie(ratedEntity.copy(createdAt = createdAt ?: ratedEntity.createdAt))
        }
    }

    override suspend fun insertMyWantMovie(
        myMovieEntity: MyMovieEntity,
        wantToWatchesEntity: WantToWatchesEntity,
        myGenres: List<Int>
    ) {
        myMovieDao.upsertMyMovie(myMovieEntity)
        myGenresDao.upsertTags(myGenres.map { MyGenresEntity(genreId = it, movieId = myMovieEntity.id) })
        myWantToWatchDao.insertWantMovie(wantToWatchesEntity)
    }

    override suspend fun deleteMyWantMovie(
        myMovieEntity: MyMovieEntity,
        wantToWatchesEntity: WantToWatchesEntity
    ) {
        myWantToWatchDao.deleteWantMovie(wantToWatchesEntity)
        if (existToMyRatedMovie(myMovieEntity.id) == null) {
            myMovieDao.deleteMyMovie(myMovieEntity)
        }
    }

    override suspend fun deleteMyRatedMovie(
        myMovieEntity: MyMovieEntity,
        ratedEntity: RatedEntity
    ) {
        myRatedDao.deleteRatedMovie(ratedEntity)
        if (existToMyWantMovie(myMovieEntity.id) == null) {
            myMovieDao.deleteMyMovie(myMovieEntity)
        }
    }

    override suspend fun existToMyRatedMovie(id: Int): RatedEntity? {
        return myRatedDao.existToMyRatedMovie(id)
    }

    override suspend fun existToMyWantMovie(id: Int): WantToWatchesEntity? {
        return myWantToWatchDao.existToMyWantMovie(id)
    }

    override fun getMyMovieRatedAndWanted(id: Int): Flow<MyMovieRatedAndWantedVO> {
        return myMovieDao.getMyMovieRatedAndWanted(id)
    }


}