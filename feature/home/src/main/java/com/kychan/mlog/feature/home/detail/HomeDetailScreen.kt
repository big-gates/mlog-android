package com.kychan.mlog.feature.home.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kychan.mlog.core.common.extenstions.roundToTheFirstDecimal
import com.kychan.mlog.core.design.component.DynamicGridComponent.DEFAULT_ROW_DYNAMIC_INDEX
import com.kychan.mlog.core.design.component.DynamicLazyVerticalGrid
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.feature.home.BuildConfig
import com.kychan.mlog.feature.home.model.MovieItem

@Composable
fun HomeDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeDetailViewModel = hiltViewModel()
){
    val movies by viewModel.movies.collectAsStateWithLifecycle()

    HomeDetailScreen(
        modifier = modifier,
        movies = movies
    )
}

@Composable
fun HomeDetailScreen(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
){
    val cols = 3
    var isPrevReverse = false
    val dynamicGridItem = movies.mapIndexed { index, movie ->
        val row = index / cols
        val isRowDynamic = row % DEFAULT_ROW_DYNAMIC_INDEX == 1
        val movieItem = MovieItem(
            image = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}w342/${movie.posterPath}",
            rank = "${index+1}",
            rating = movie.voteAverage.roundToTheFirstDecimal().toFloat(),
            title = movie.title,
            isRowDynamic = isRowDynamic,
            isReverse = isPrevReverse
        )
        if(isRowDynamic) isPrevReverse = !isPrevReverse

        movieItem
    }

    DynamicLazyVerticalGrid(
        cols = cols,
        height = 150,
        items = dynamicGridItem,
        dynamicGridContent = { movie ->
            Movie(
                movie = movie
            )
        },
        normalGridContent = { movie ->
            Movie(
                modifier = Modifier
                    .heightIn(max = 150.dp),
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