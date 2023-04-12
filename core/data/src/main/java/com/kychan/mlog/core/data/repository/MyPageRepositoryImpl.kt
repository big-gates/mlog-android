package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import com.kychan.mlog.core.model.MyMovie
import com.kychan.mlog.core.model.MyRatedMovies
import com.kychan.mlog.core.model.Rated
import com.kychan.mlog.core.model.WantToWatch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myMovieRoomDataSource: MyMovieRoomDataSource
) : MyPageRepository {
    override fun getMyRatedMovies(): Flow<List<MyRatedMovies>> {
        return myMovieRoomDataSource.getMyRatedMovies().map { it.map(MyRatedMoviesVO::toDomain) }
    }

    override suspend fun insertMyWantMovie(myMovie: MyMovie, wantToWatch: WantToWatch) {
        return myMovieRoomDataSource.insertMyWantMovie(MyMovieEntity.of(myMovie), WantToWatchesEntity.of(wantToWatch))
    }
}