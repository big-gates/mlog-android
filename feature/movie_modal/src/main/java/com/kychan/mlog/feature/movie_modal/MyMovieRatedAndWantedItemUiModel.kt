package com.kychan.mlog.feature.movie_modal

import com.kychan.mlog.core.model.MyMovieRatedAndWanted

data class MyMovieRatedAndWantedItemUiModel(
    val rated: Float,
    val comment: String,
    val isLike: Boolean,
)

fun MyMovieRatedAndWanted.toMyMovieRatedAndWantedItemUiModel() = MyMovieRatedAndWantedItemUiModel(
    rated = rated,
    comment = comment,
    isLike = isLike,
)