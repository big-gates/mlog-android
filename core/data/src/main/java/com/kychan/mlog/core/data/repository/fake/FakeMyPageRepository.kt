package com.kychan.mlog.core.data.repository.fake

import com.kychan.mlog.core.data.repository.MyPageRepository
import com.kychan.mlog.core.dataSourceLocal.room.datasource.fake.FakeMyMovieRoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import com.kychan.mlog.core.dataSourceLocal.room.model.MyWantToWatchMovieVO
import com.kychan.mlog.core.dataSourceLocal.room.model.RatedEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WantToWatchesEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.toDomain
import com.kychan.mlog.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeMyPageRepository @Inject constructor(
    private val myMovieRoomDataSource: FakeMyMovieRoomDataSource,
) : MyPageRepository {
    override fun getMyRatedMovies(): Flow<List<MyRatedMovies>> {
        return myMovieRoomDataSource.getMyRatedMovies().map { it.map(MyRatedMoviesVO::toDomain) }
    }

    override fun getMyWantToWatchMovies(): Flow<List<MyWantToWatchMovie>> {
        return myMovieRoomDataSource.getMyWantToWatchMovies().map { it.map(MyWantToWatchMovieVO::toDomain) }
    }

    override suspend fun updateMyRatedMovie(myMovie: MyMovie, rated: Rated, myGenres: List<Int>) {
        return myMovieRoomDataSource.updateMyRatedMovie(MyMovieEntity.of(myMovie), RatedEntity.of(rated), myGenres)
    }

    override suspend fun insertMyWantMovie(
        myMovie: MyMovie,
        wantToWatch: WantToWatch,
        myGenres: List<Int>
    ) {
        return myMovieRoomDataSource.insertMyWantMovie(MyMovieEntity.of(myMovie), WantToWatchesEntity.of(wantToWatch), myGenres)
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

    override suspend fun existToMyWantMovie(id: Int): WantToWatch? {
        return myMovieRoomDataSource.existToMyWantMovie(id)?.toDomain()
    }
}