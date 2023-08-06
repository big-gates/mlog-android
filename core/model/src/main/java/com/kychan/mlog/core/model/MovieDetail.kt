package com.kychan.mlog.core.model

/**
 * Response데이터를 전부 파싱하지 않음.
 *
 * 주석 처리 = 미파싱
 */
data class MovieDetail(
    val adult: Boolean,
    val backdropPath: String?,
    val belongsToCollection: Any?,
    val budget: Int,
    val genres: List<Genre2>,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
//    val productionCompanies: List<ProductionCompany>,
//    val productionCountries: List<ProductionCountry>,
    val releaseDate: String, //format: date
    val revenue: Long,
    val runtime: Int?,
//    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
