package com.kychan.mlog.core.data.repository

import com.kychan.mlog.core.data.mapper.TvSeriesMapper
import com.kychan.mlog.core.data.mapper.toDomain
import com.kychan.mlog.core.dataSourceRemote.http.datasource.tmdb.TMDBDataSource
import com.kychan.mlog.core.model.*
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

    override suspend fun getMoviePopularWithProvider(
        page: Int,
        language: Language,
        watchRegion: WatchRegion,
        withWatchProviders: WatchProviders
    ): List<Movie> {
        return tmdbDataSource.getMoviePopularWithProvider(
            page,
            language,
            watchRegion,
            withWatchProviders
        ).toDomain()
    }
}