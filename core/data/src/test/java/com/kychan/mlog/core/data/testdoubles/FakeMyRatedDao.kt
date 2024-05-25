package com.kychan.mlog.core.data.testdoubles

import com.kychan.mlog.core.dataSourceLocal.room.dao.MyRatedDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import com.kychan.mlog.core.dataSourceLocal.room.model.RatedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class FakeMyRatedDao(
    private val myMovieDao: FakeMyMovieDao,
    private val myGenresDao: FakeMyGenresDao
) : MyRatedDao {

    private val ratedEntityStateFlow = MutableStateFlow(emptyList<RatedEntity>())
    override fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>> {

        val myRatedMovies = combine(
            myMovieDao.getMyMovies(),
            myGenresDao.getMyGenres(),
            ratedEntityStateFlow,
        ) { myMovies, myGenres, ratedEntityList ->
            myMovies.map { myMovie ->
                MyRatedMoviesVO(
                    myMovie = myMovie,
                    rated = ratedEntityList.find { it.myMovieId == myMovie.id }!!,
                    tags = myGenres.filter { it.movieId == myMovie.id },
                )
            }
        }
        return myRatedMovies
    }

    override suspend fun upsertRatedMovie(ratedEntity: RatedEntity) {
        ratedEntityStateFlow.update { oldValues ->
            (oldValues + ratedEntity).distinctBy(RatedEntity::id)
        }
    }

    override suspend fun deleteRatedMovie(ratedEntity: RatedEntity) {
        ratedEntityStateFlow.update { entities ->
            entities.filterNot { it.id == ratedEntity.id }
        }
    }

    override suspend fun existToMyRatedMovie(id: Int): RatedEntity? {
        return ratedEntityStateFlow.value.find { it.id == id }
    }

}