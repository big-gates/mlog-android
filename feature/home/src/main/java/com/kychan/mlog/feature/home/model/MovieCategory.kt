package com.kychan.mlog.feature.home.model

import com.kychan.mlog.core.model.WatchProviders

data class MovieCategory(
    val title: String,
    val movieItems: List<MovieItem>,
    val watchProviders: WatchProviders,
)
