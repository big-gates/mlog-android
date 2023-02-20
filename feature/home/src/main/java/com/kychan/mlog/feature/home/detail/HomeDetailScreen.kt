package com.kychan.mlog.feature.home.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kychan.mlog.core.design.component.DynamicGridComponent.DEFAULT_COL
import com.kychan.mlog.core.design.component.PagingDynamicLazyVerticalGrid
import com.kychan.mlog.feature.home.model.MovieItem

@Composable
fun HomeDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeDetailViewModel = hiltViewModel()
){
    val movies = viewModel.movies.collectAsLazyPagingItems()

    HomeDetailScreen(
        modifier = modifier,
        movies = movies
    )
}

@Composable
fun HomeDetailScreen(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<MovieItem>,
){
    PagingDynamicLazyVerticalGrid(
        cols = DEFAULT_COL,
        height = 150,
        items = movies,
        content = { movie ->
            Movie(
                movie = movie
            )
        }
    )
}

@Composable
fun Movie(
    modifier: Modifier = Modifier,
    movie: MovieItem,
) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = movie.image).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
        ),
        contentDescription = "movie poster",
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxWidth()
    )
}