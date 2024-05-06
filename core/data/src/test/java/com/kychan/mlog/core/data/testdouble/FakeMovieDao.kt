package com.kychan.mlog.core.data.testdouble

import androidx.paging.PagingSource
import com.kychan.mlog.core.dataSourceLocal.room.dao.MovieDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.TagDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.WatchProviderDao
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class FakeMovieDao(
    private val watchProviderDao: WatchProviderDao,
    private val tagDao: TagDao,
): MovieDao {

    private val entitiesStateFlow = MutableStateFlow(emptyList<MovieEntity>())

    override fun getMovies(movieTypeId: Int): PagingSource<Int, MovieVO> {
        val data = combine(
            entitiesStateFlow,
            watchProviderDao.getWatchProviders(),
            tagDao.getTags()
        ){ movieList, watchProviderList, tagList ->
            val filteredWatchProviderList = watchProviderList.filter { it.watchProviderId == movieTypeId }
            filteredWatchProviderList.map { filteredWatchProvider ->
                movieList.filter { movie -> movie.id == filteredWatchProvider.movieId }
            }.flatten().map { movie ->
                MovieVO(
                    movie = movie,
                    watchProviders = watchProviderList.filter { it.movieId == movie.id },
                    tags = tagList.filter { it.movieId == movie.id }
                )
            }
        }

        return FlowPagingSource(data)
    }

    override fun getMovies(): Flow<List<MovieEntity>> {
        return entitiesStateFlow
    }

    override suspend fun upsertMovies(entities: List<MovieEntity>) {
        entitiesStateFlow.update { entities }

    }

    override suspend fun deleteMovies(ids: List<Int>) {
        entitiesStateFlow.update { prev ->
            prev.filter { ids.contains(it.id).not() }
        }
    }
}