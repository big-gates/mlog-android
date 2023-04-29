package com.kychan.mlog.feature.movie_modal

import com.kychan.mlog.core.model.Rated

data class RateItem(
    val id: Int = -1,
    val myMovieId: Int = -1,
    val rate: Float = 0f,
    val comment: String = "",
)

fun Rated?.toRateItem() = RateItem(
    id = this?.id ?: -1,
    myMovieId = this?.myMovieId ?: -1,
    rate = this?.rated ?: 0f,
    comment = this?.comment.orEmpty(),
)
