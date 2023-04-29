package com.kychan.mlog.feature.movie_modal

data class MovieModalState(
    val isRatedState: RateItem,
    val isLikeState: Boolean = false,
    val onShowModal: (item: MovieModalTO) -> Unit = {},
    val modalEvent: MovieModalEvent,
)

data class MovieModalEvent(
    val onLikeClick: () -> Unit = {},
    val onTextChange: (String) -> Unit = {},
    val onRateChange: (Float) -> Unit = {},
)