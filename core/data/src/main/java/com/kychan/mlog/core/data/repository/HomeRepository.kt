package com.kychan.mlog.core.data.repository

import com.kychan.core.entity.TvSeriesEntity

interface HomeRepository {
    fun getBoxOfficeRanking()
    suspend fun getWatchaRanking(page: Int, language: String): List<TvSeriesEntity>
    fun getNeflixRanking()
}