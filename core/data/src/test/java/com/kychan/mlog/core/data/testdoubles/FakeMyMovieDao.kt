package com.kychan.mlog.core.data.testdoubles

import com.kychan.mlog.core.dataSourceLocal.room.dao.MyMovieDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieRatedAndWantedVO
import com.kychan.mlog.core.dataSourceLocal.room.model.RatedEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WantToWatchesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeMyMovieDao : MyMovieDao {

    private val entitiesStateFlow = MutableStateFlow(emptyList<MyMovieEntity>())
    private val ratedEntityStateFlow = MutableStateFlow(emptyList<RatedEntity>())
    private val wantToWatchesEntity = MutableStateFlow(emptyList<WantToWatchesEntity>())
    private val myMovieInfoStateFlow = MutableStateFlow(MyMovieRatedAndWantedVO(null, null, null))
    override fun getMyMovies(): Flow<List<MyMovieEntity>> {
        return entitiesStateFlow
    }

    override suspend fun upsertMyMovie(myMovieEntity: MyMovieEntity) {
        entitiesStateFlow.update { oldValues ->
            (oldValues + myMovieEntity).distinctBy(MyMovieEntity::id)
        }
    }

    override suspend fun deleteMyMovie(myMovieEntity: MyMovieEntity) {
        entitiesStateFlow.update { entities ->
            entities.filterNot { it.id == myMovieEntity.id }
        }
    }

    override fun getMyMovieRatedAndWanted(id: Int): Flow<MyMovieRatedAndWantedVO> {
        /*
        mymovie, rated, wantToWatch
        myMovieId와 같은 id의 컨텐츠를 가져온다~
         */
        val rated = ratedEntityStateFlow.value.find { it.myMovieId == id }
        val wantToWatch = wantToWatchesEntity.value.find { it.myMovieId == id }
        myMovieInfoStateFlow.update {
            MyMovieRatedAndWantedVO(
                rated = rated?.rated,
                comment = rated?.comment,
                wantToMovieId = wantToWatch?.myMovieId,
            )
        }

        return myMovieInfoStateFlow
    }
}