package com.kychan.mlog.core.data.testdouble

import com.kychan.mlog.core.dataSourceLocal.room.dao.TagDao
import com.kychan.mlog.core.dataSourceLocal.room.model.GenresEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeTagDao: TagDao {

    private val entitiesStateFlow = MutableStateFlow(emptyList<GenresEntity>())
    override suspend fun upsertTags(entities: List<GenresEntity>) {
        entitiesStateFlow.update { entities }
    }

    override fun getTags(): Flow<List<GenresEntity>> {
        return entitiesStateFlow
    }
}