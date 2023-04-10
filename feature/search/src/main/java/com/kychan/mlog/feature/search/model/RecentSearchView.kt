package com.kychan.mlog.feature.search.model

import com.kychan.mlog.core.model.RecentSearch

data class RecentSearchView(
    val id: Int,
    val text: String,
)

fun RecentSearch.toView() = RecentSearchView(
    id = id,
    text = text
)
