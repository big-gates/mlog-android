package com.kychan.mlog.core.design.component.movie_modal

data class MovieModalTO(
    val id: Int,
    val title: String,
    val adult: Boolean,
    val backgroundImage: String,
    val tags: List<String>,
)