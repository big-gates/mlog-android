package com.kychan.mlog.core.data.testdoubles

import com.kychan.mlog.core.dataSourceLocal.room.dao.MyWantToWatchDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MyGenresEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import com.kychan.mlog.core.dataSourceLocal.room.model.MyWantToWatchMovieVO
import com.kychan.mlog.core.dataSourceLocal.room.model.WantToWatchesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeMyWantToWatchDao : MyWantToWatchDao {

    private val entitiesStateFlow = MutableStateFlow(emptyList<MyWantToWatchMovieVO>())
    private val wantToWatchEntityStateFlow = MutableStateFlow(emptyList<WantToWatchesEntity>())
    private val myMovieEntityStateFlow = MutableStateFlow(emptyList<MyMovieEntity>())
    private val myGenresEntityStateFlow = MutableStateFlow(emptyList<MyGenresEntity>())
    override fun getMyWantToWatchMovies(): Flow<List<MyWantToWatchMovieVO>> {

        /*
        여기서 myMovie데이터랑 Tags데이터를 가져와야해
        그럼 여기 클래스 내부 함수를 사용서 upsert해줄 때마다 저 두 데이터를 가져와서 치환하는 방식으로인데.
         */
        entitiesStateFlow.update { ratedVO ->
            ratedVO + myMovieEntityStateFlow.value.map { myMovie ->
                MyWantToWatchMovieVO(
                    myMovie = myMovie,
                    wantToWatch = wantToWatchEntityStateFlow.value.find { it.myMovieId == myMovie.id }!!,
                    tags = myGenresEntityStateFlow.value.filter { it.movieId == myMovie.id },
                )
            }
        }

        return entitiesStateFlow
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