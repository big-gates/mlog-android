package com.kychan.core.data.mapper

import com.kychan.core.dataSourceRemote.http.model.TvSeries
import com.kychan.core.entity.TvSeriesEntity
import org.mapstruct.Mapper

@Mapper
interface TvSeriesMapper {
    fun toEntity(data: List<TvSeries>): List<TvSeriesEntity>
}