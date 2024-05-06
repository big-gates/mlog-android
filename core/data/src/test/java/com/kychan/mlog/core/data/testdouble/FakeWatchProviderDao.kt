package com.kychan.mlog.core.data.testdouble

import com.kychan.mlog.core.dataSourceLocal.room.dao.WatchProviderDao
import com.kychan.mlog.core.dataSourceLocal.room.model.WatchProviderEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeWatchProviderDao: WatchProviderDao {
    private val entitiesStateFlow = MutableStateFlow(emptyList<WatchProviderEntity>())
    override suspend fun upsertWatchProviders(entities: List<WatchProviderEntity>) {
        entitiesStateFlow.update { entities }
    }

    override fun getMovieIds(watchProviderId: Int): Flow<List<Int>> {
        return entitiesStateFlow.map { watchProviderList ->
            watchProviderList.filter { it.watchProviderId == watchProviderId }.map { it.movieId }
        }
    }

    override fun getWatchProviders(): Flow<List<WatchProviderEntity>> {
        return entitiesStateFlow
    }
}