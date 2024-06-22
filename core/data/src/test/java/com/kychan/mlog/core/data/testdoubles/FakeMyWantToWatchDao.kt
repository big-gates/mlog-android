package com.kychan.mlog.core.data.testdoubles

import com.kychan.mlog.core.dataSourceLocal.room.dao.MyWantToWatchDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MyWantToWatchMovieVO
import com.kychan.mlog.core.dataSourceLocal.room.model.WantToWatchesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class FakeMyWantToWatchDao(
    private val myMovieDao: FakeMyMovieDao,
    private val myGenresDao: FakeMyGenresDao
) : MyWantToWatchDao {

    private val wantToWatchEntityStateFlow = MutableStateFlow(emptyList<WantToWatchesEntity>())
    override fun getMyWantToWatchMovies(): Flow<List<MyWantToWatchMovieVO>> {

        val data = combine(
            myMovieDao.getMyMovies(), myGenresDao.getMyGenres()
        ) { myMovies, myGenres ->
            myMovies.map { myMovie ->
                MyWantToWatchMovieVO(
                    myMovie = myMovie,
                    wantToWatch = wantToWatchEntityStateFlow.value.find { it.myMovieId == myMovie.id }!!,
                    tags = myGenres.filter { it.movieId == myMovie.id },
                )
            }
        }

        return data
    }

    override suspend fun insertWantMovie(wantToWatchesEntity: WantToWatchesEntity) {
        wantToWatchEntityStateFlow.update { oldValues ->
            (oldValues + wantToWatchesEntity).distinctBy(WantToWatchesEntity::id)
        }
    }

    override suspend fun deleteWantMovie(wantToWatchesEntity: WantToWatchesEntity) {
        wantToWatchEntityStateFlow.update { entities ->
            entities.filterNot { it.id == wantToWatchesEntity.id }
        }
    }

    override suspend fun existToMyWantMovie(id: Int): WantToWatchesEntity? {
        return wantToWatchEntityStateFlow.value.find { it.id == id }
    }
}