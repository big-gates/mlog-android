package com.kychan.mlog.core.data.testdoubles

import com.kychan.mlog.core.dataSourceLocal.room.dao.MyGenresDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MyGenresEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeMyGenresDao : MyGenresDao {

    private val entitiesStateFlow = MutableStateFlow(emptyList<MyGenresEntity>())
    override fun getMyGenres(): Flow<List<MyGenresEntity>> {
        return entitiesStateFlow
    }

    override suspend fun upsertTags(entities: List<MyGenresEntity>) {
        entitiesStateFlow.update { oldValues ->
            (oldValues + entities).distinctBy(MyGenresEntity::genreId)
        }
    }
}