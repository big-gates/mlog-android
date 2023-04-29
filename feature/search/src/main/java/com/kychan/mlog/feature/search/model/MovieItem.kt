package com.kychan.mlog.feature.search.model

import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.feature.search.BuildConfig

data class MovieItem(
    val id: Int,
    val image: String,
)

fun Movie.toView(posterSize: String) = MovieItem(
    id = id,
    image = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}${posterSize}/${posterPath}",
)
