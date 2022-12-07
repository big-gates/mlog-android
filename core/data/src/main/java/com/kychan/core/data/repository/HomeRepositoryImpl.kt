package com.kychan.core.data.repository

import com.kychan.core.data.mapper.TvSeriesMapper
import com.kychan.core.dataSourceRemote.http.datasource.tmdb.TMDBDataSource
import com.kychan.core.entity.TvSeriesEntity
import org.mapstruct.factory.Mappers
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val tmdbDataSource: TMDBDataSource
): HomeRepository {

    private val tvSeriesMapper: TvSeriesMapper by lazy {
        Mappers.getMapper(TvSeriesMapper::class.java)
    }
    override fun getBoxOfficeRanking() {

    }

    override suspend fun getWatchaRanking(page: Int, language: String): List<TvSeriesEntity> {
        return tvSeriesMapper.toEntity(tmdbDataSource.getTvSeriesPopular(page, language).results)
    }

    override fun getNeflixRanking() {
        TODO("Not yet implemented")
    }
}