package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.ColumnInfo
import com.kychan.mlog.core.model.MyRatedMovies
import com.kychan.mlog.core.model.WatchProviders

data class MyRatedMoviesVO(
    @ColumnInfo("my_movie_id") val myMovieId: Int,
    val adult: Boolean,
    @ColumnInfo("backdrop_path") val backdropPath: String,
    @ColumnInfo("original_title") val originalTitle: String,
    @ColumnInfo("poster_path") val posterPath: String,
    val title: String,
    @ColumnInfo("vote_average") val voteAverage: Double,
    @ColumnInfo("watch_providers") val watchProviders: WatchProviders,
    val rank: Int,
    val rated: Float,
    val comment: String,
)

fun MyRatedMoviesVO.toDomain() = MyRatedMovies(
    myMovieId = myMovieId,
    adult = adult,
    backdropPath = backdropPath,
    originalTitle = originalTitle,
    posterPath = posterPath,
    title = title,
    voteAverage = voteAverage,
    watchProviders = watchProviders,
    rank = rank,
    rated = rated,
    comment = comment,
)