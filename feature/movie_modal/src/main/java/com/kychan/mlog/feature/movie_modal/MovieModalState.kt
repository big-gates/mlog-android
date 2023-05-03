package com.kychan.mlog.feature.movie_modal

data class MovieModalState(
    val isRatedState: RateItem,
    val isLikeState: Boolean,
    val onShowModal: (item: MovieModalUiModel) -> Unit,
    val modalEvent: MovieModalEvent,
)

data class MovieModalEvent(
    val onLikeClick: () -> Unit,
    val onTextChange: (String, Float) -> Unit,
    val onRateChange: (String, Float) -> Unit,
)