package com.kychan.mlog.feature.movie_modal

data class MovieModalUiState(
    val movieModalUiModel: MovieModalUiModel,
    val isRatedState: RateItem,
    val isLikeState: Boolean,
    val modalEvent: MovieModalEvent,
)

data class MovieModalEvent(
    val onLikeClick: () -> Unit,
    val onTextChange: (String, Float) -> Unit,
    val onRateChange: (String, Float) -> Unit,
)