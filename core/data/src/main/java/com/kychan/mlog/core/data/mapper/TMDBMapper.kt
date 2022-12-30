package com.kychan.mlog.core.data.mapper

import com.kychan.mlog.core.dataSourceRemote.http.model.TvSeries
import com.kychan.mlog.core.model.TvSeriesEntity
import org.mapstruct.Mapper

@Mapper
interface TvSeriesMapper {
    fun toEntity(data: List<TvSeries>): List<TvSeriesEntity>
}