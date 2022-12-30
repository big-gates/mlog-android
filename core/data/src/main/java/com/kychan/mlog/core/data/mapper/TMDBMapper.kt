package com.kychan.mlog.core.data.mapper

import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.TvSeries
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.TvSeriesEntity
import org.mapstruct.Mapper

@Mapper
interface TvSeriesMapper {
    fun toEntity(data: List<TvSeries>): List<TvSeriesEntity>
}

fun MovieDiscoverRes.toDomain(): List<Movie> = this.results.map {
    Movie(
        id = it.id,
        adult = it.adult,
        backdropPath = it.backdropPath,
        originalTitle = it.originalTitle,
        posterPath = it.posterPath,
        title = it.title,
        voteAverage = it.voteAverage
    )
}