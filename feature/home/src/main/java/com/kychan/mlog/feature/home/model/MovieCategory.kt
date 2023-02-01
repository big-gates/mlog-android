package com.kychan.mlog.feature.home.model

import com.kychan.mlog.core.model.WatchProvider

data class MovieCategory(
    val title: String,
    val movieItems: List<MovieItem>,
    val watchProvider: WatchProvider,
)
