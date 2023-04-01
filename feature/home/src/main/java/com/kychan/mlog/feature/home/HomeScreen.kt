package com.kychan.mlog.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.kychan.mlog.core.design.icon.MLogIcons
import com.kychan.mlog.core.design.theme.Black
import com.kychan.mlog.core.design.theme.MovieRankBg
import com.kychan.mlog.core.design.theme.Pink500
import com.kychan.mlog.core.design.util.maxScrollFlingBehavior
import com.kychan.mlog.core.model.WatchProvider
import com.kychan.mlog.feature.home.HomeViewModel.Companion.MLOG_RECOMMENDATION
import com.kychan.mlog.feature.home.HomeViewModel.Companion.NETFLIX_RECOMMENDATION
import com.kychan.mlog.feature.home.HomeViewModel.Companion.WATCHA_RECOMMENDATION
import com.kychan.mlog.feature.home.model.Header
import com.kychan.mlog.feature.home.model.MovieCategory
import com.kychan.mlog.feature.home.model.MovieItem

@Composable
fun HomeAppBar(navigateToSearch: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
    ) {
        Image(
            painter = painterResource(id = MLogIcons.Logo),
            contentDescription = "",
            contentScale = ContentScale.Fit
        )

        Image(
            modifier = Modifier.clickable { navigateToSearch() },
            painter = painterResource(id = MLogIcons.Search),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Black),
        )
    }
}

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToHomeDetail: (watchProvider: WatchProvider) -> Unit,
    navigateToSearch: () -> Unit
) {
    HomeScreen(
        categories = listOf(
            MovieCategory(
                header = Header(MLOG_RECOMMENDATION, WatchProvider.None),
                movieItem = viewModel.mLogMovieItem.collectAsLazyPagingItems()
            ),
            MovieCategory(
                header = Header(NETFLIX_RECOMMENDATION, WatchProvider.Netflix),
                movieItem = viewModel.netflixMovieItem.collectAsLazyPagingItems()
            ),
            MovieCategory(
                header = Header(WATCHA_RECOMMENDATION, WatchProvider.Watcha),
                movieItem = viewModel.watchaMovieitem.collectAsLazyPagingItems()
            )
        ),
        navigateToHomeDetail = navigateToHomeDetail,
        navigateToSearch = navigateToSearch,
    )
}

@Composable
fun HomeScreen(
    categories: List<MovieCategory>,
    navigateToHomeDetail: (watchProvider: WatchProvider) -> Unit = { },
    navigateToSearch: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(Modifier.scrollable(
        state = scrollState,
        orientation = Orientation.Vertical
    )) {
        HomeAppBar(navigateToSearch = navigateToSearch)

        LazyColumn(
            contentPadding = PaddingValues(5.dp),
            flingBehavior = maxScrollFlingBehavior(0F),
        ){
            items(
                items = categories
            ){
                MovieRankingsByCategory(
                    header = it.header,
                    movie = it.movieItem,
                    navigateToHomeDetail = navigateToHomeDetail
                )
            }
        }
    }
}

@Composable
fun MovieRankingsByCategory(
    header: Header,
    movie: LazyPagingItems<MovieItem>,
    navigateToHomeDetail: (watchProvider: WatchProvider) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .heightIn(min = 270.dp)
    ) {

        CategoryTitle(header, navigateToHomeDetail)

        LazyRow(
            contentPadding = PaddingValues(horizontal = 4.dp),
            flingBehavior = maxScrollFlingBehavior(5000F),
        ) {
            items(
                key = { it.id },
                items = movie
            ) { movie ->
                if (movie != null) {
                    Movie(movie = movie)
                }
            }
        }
    }
}

@Composable
fun CategoryTitle(
    category: Header,
    navigateToHomeDetail: (watchProvider: WatchProvider) -> Unit
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 4.dp)
            .clickable { navigateToHomeDetail(category.watchProvider) }
    ) {
        Text(
            text = category.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = MLogIcons.RightArrow),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(12.dp)
                .height(12.dp)
        )
    }
}

@Composable
fun Movie(
    movie: MovieItem,
) {
    Column(
        modifier = Modifier
            .width(MOVIE_ITEM_WIDTH.dp)
            .padding(end = 7.dp)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = movie.image)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        })
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .build()
                ),
                contentDescription = "movie poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(MOVIE_ASPECT_RATIO, true)
            )

            MovieRankBox(
                rank = movie.rank,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 2.5.dp, bottom = 2.5.dp)
            )
        }

        Text(
            text = movie.title,
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 5.dp),
            lineHeight = 17.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        MovieRating(
            rating = movie.rating,
            modifier = Modifier.padding(top = 5.dp)
        )
    }
}

@Composable
fun MovieRankBox(
    modifier: Modifier = Modifier,
    rank: String
) {
    Row(
        modifier = modifier
            .width(20.dp)
            .height(20.dp)
            .background(
                color = MovieRankBg,
                shape = RoundedCornerShape(5.dp)
            ),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = rank,
            fontSize = 10.sp,
            color = Color.White,
        )
    }
}

@Composable
fun MovieRating(
    modifier: Modifier = Modifier,
    rating: Float
) {
    Row(modifier = modifier) {
        Text(
            text = "예상 ★ $rating",
            color = Pink500,
            fontSize = 13.sp
        )
    }
}