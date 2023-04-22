package com.kychan.mlog.feature.movie_modal

import com.kychan.mlog.core.model.Rated

data class MovieModalState(
    val isRatedState: Rated? = null,
    val isLikeState: Boolean = false,
    val onShowModal: (item: MovieModalTO) -> Unit = {},
    val modalEvent: MovieModalEvent,
)

data class MovieModalEvent(
    val onLikeClick: () -> Unit = {},
    val onTextChange: (String) -> Unit = {},
    val onRateChange: (Float) -> Unit = {},
)