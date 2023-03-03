package com.kychan.mlog.feature.home.model

import com.kychan.mlog.core.design.component.DynamicGridItem

data class MovieItem(
    val image: String,
    val rank: String,
    val rating: Float,
    val title: String,
    override val isRowDynamic: Boolean = false,
    override val isReverse: Boolean = false
): DynamicGridItem
