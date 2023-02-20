package com.kychan.mlog.feature.home.model

import androidx.paging.compose.LazyPagingItems
import com.kychan.mlog.core.common.extenstions.roundToTheFirstDecimal
import com.kychan.mlog.core.design.component.DynamicGridItem
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.WatchProvider
import com.kychan.mlog.feature.home.BuildConfig
import com.kychan.mlog.feature.home.HomeViewModel

data class MovieCategory(
    val header: Header,
    val movieItem: LazyPagingItems<MovieItem>
)

data class Header(
    val title: String,
    val watchProvider: WatchProvider,
)

data class MovieItem(
    val image: String,
    val rank: String,
    val rating: Float,
    val title: String,
    override val isRowDynamic: Boolean = false,
    override val isReverse: Boolean = false
): DynamicGridItem

fun Movie.toView() = MovieItem(
    image = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}${HomeViewModel.POSTER_SIZE}/${posterPath}",
    rank = "$rank",
    rating = voteAverage.roundToTheFirstDecimal().toFloat(),
    title = title,
)
