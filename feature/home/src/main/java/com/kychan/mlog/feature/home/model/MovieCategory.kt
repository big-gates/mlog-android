package com.kychan.mlog.feature.home.model

import androidx.paging.compose.LazyPagingItems
import com.kychan.mlog.core.common.extenstions.roundToTheFirstDecimal
import com.kychan.mlog.core.design.component.DynamicGridComponent
import com.kychan.mlog.core.design.component.DynamicGridItem
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.feature.home.BuildConfig

data class MovieCategory(
    val header: Header,
    val movieItem: LazyPagingItems<MovieItem>
)

data class Header(
    val title: String,
    val watchProviderId: Int,
)

data class MovieItem(
    val id: Int,
    val posterPath: String,
    val image: String,
    val rank: String,
    val rating: Float,
    val title: String,
    val adult: Boolean,
    override val isRowDynamic: Boolean = false,
    override val isReverse: Boolean = false
): DynamicGridItem

fun Movie.toView(
    posterSize: String,
    watchProviderId: Int,
    isReverse: Boolean = false,
) = MovieItem(
    id = id,
    posterPath = posterPath,
    image = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}${posterSize}/${posterPath}",
    rank = "${watchProvider.find { it.id == watchProviderId }?.rank}",
    rating = voteAverage.roundToTheFirstDecimal().toFloat(),
    title = title,
    adult = adult,
    isRowDynamic = watchProvider.find { it.id == watchProviderId }?.rank?.rem(DynamicGridComponent.DEFAULT_ROW_DYNAMIC_INDEX) == 0,
    isReverse = isReverse
)
