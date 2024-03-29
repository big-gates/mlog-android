package com.kychan.mlog.core.dataSourceRemote.http.model

import com.kychan.mlog.core.model.Genre2
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TvSeriesDetailRes(
    val id: Int,
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("created_by") val createdBy: List<CreatedBy>,
    @SerialName("episode_run_time") val episodeRunTime: List<Int>,
    @SerialName("first_air_date") val firstAirDate: String,
    val genres: List<Genre>,
    val homepage: String,
    @SerialName("in_production") val inProduction: Boolean,
    val languages: List<String>,
    @SerialName("last_air_date") val lastAirDate: String,
    @SerialName("last_episode_to_air") val lastEpisodeToAir: LastEpisodeToAir,
    val name: String,
    val networks: List<Network>,
    @SerialName("number_of_episodes") val numberOfEpisodes: Int,
    @SerialName("number_of_seasons") val numberOfSeasons: Int,
    @SerialName("origin_country") val originCountry: List<String>,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_name") val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("production_companies") val productionCompanies: List<ProductionCompany>,
    @SerialName("production_countries") val productionCountries: List<ProductionCountry>,
    val seasons: List<Season>,
    @SerialName("spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

@Serializable
data class CreatedBy(
    @SerialName("credit_id") val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    @SerialName("profile_path") val profilePath: String?
)

@Serializable
data class Genre(
    val id: Int,
    val name: String
)
fun Genre.toDomain() = Genre2(
    id = id,
    name = name,
)

@Serializable
data class LastEpisodeToAir(
    @SerialName("air_date") val airDate: String,
    @SerialName("episode_number") val episodeNumber: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName("production_code") val productionCode: String,
    val runtime: Int,
    @SerialName("season_number") val seasonNumber: Int,
    @SerialName("show_id") val showId: Int,
    @SerialName("still_path") val stillPath: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

@Serializable
data class Network(
    val id: Int,
    @SerialName("logo_path") val logoPath: String?,
    val name: String,
    @SerialName("origin_country") val originCountry: String
)

@Serializable
data class ProductionCompany(
    val id: Int,
    @SerialName("logo_path") val logoPath: String?,
    val name: String,
    @SerialName("origin_country") val originCountry: String
)

@Serializable
data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
)

@Serializable
data class Season(
    @SerialName("air_date") val airDate: String,
    @SerialName("episode_count") val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("season_number") val seasonNumber: Int
)

@Serializable
data class SpokenLanguage(
    @SerialName("english_name") val englishName: String,
    val iso_639_1: String,
    val name: String
)