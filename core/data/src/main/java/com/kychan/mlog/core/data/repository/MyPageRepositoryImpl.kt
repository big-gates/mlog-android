package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.dataSourceLocal.room.datasource.MyMovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import com.kychan.mlog.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myMovieRoomDataSource: MyMovieRoomDataSource
) : MyPageRepository {
    override fun getMyRatedMovies(): Flow<List<MyRatedMovies>> {
        return myMovieRoomDataSource.getMyRatedMovies().map { it.map(MyRatedMoviesVO::toDomain) }
    }

    override fun getMyWantToWatchMovies(): Flow<List<MyMovie>> {
        return myMovieRoomDataSource.getMyWantToWatchMovies().map { list ->
            list.map {
                it.toDomain()
            }
        }
    }

    override suspend fun updateMyRatedMovie(myMovie: MyMovie, rated: Rated) {
        return myMovieRoomDataSource.updateMyRatedMovie(MyMovieEntity.of(myMovie), RatedEntity.of(rated))
    }

    override suspend fun insertMyWantMovie(myMovie: MyMovie, wantToWatch: WantToWatch) {
        return myMovieRoomDataSource.insertMyWantMovie(MyMovieEntity.of(myMovie), WantToWatchesEntity.of(wantToWatch))
    }

    override suspend fun deleteMyWantMovie(myMovie: MyMovie, wantToWatch: WantToWatch) {
        return myMovieRoomDataSource.deleteMyWantMovie(MyMovieEntity.of(myMovie), WantToWatchesEntity.of(wantToWatch))

    }

    override suspend fun deleteMyRatedMovie(myMovie: MyMovie, rated: Rated) {
        return myMovieRoomDataSource.deleteMyRatedMovie(MyMovieEntity.of(myMovie), RatedEntity.of(rated))
    }

    override suspend fun existToMyRatedMovie(id: Int): Rated? {
        return myMovieRoomDataSource.existToMyRatedMovie(id)?.toDomain()
    }

    override suspend fun existToMyWantMovie(id: Int): Int {
        return myMovieRoomDataSource.existToMyWantMovie(id)
    }
}