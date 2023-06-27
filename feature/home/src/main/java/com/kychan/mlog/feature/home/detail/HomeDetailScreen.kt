package com.kychan.mlog.feature.home.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.kychan.mlog.core.design.component.DynamicGridComponent.DEFAULT_COL
import com.kychan.mlog.core.design.component.PagingDynamicLazyVerticalGrid
import com.kychan.mlog.feature.home.MOVIE_ASPECT_RATIO
import com.kychan.mlog.feature.home.model.MovieItem
import com.kychan.mlog.feature.movie_modal.BottomSheetLayout
import com.kychan.mlog.feature.movie_modal.ModalAction
import com.kychan.mlog.feature.movie_modal.MovieModalUiModel
import com.kychan.mlog.feature.movie_modal.MovieModalUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeDetailViewModel = hiltViewModel(),
    navigateToMovieDetail: (id: Int) -> Unit,
){
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val movieModalUiModel by viewModel.movieModalUiModel.collectAsStateWithLifecycle()
    val myMovieRatedAndWantedItemUiModel by viewModel.myMovieRatedAndWantedItemUiModel.collectAsStateWithLifecycle()
    val action: ModalAction = viewModel

    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            it != ModalBottomSheetValue.Expanded
        },
        skipHalfExpanded = false
    )

    BottomSheetLayout(
        modalSheetState = modalSheetState,
        movieModalUiState = MovieModalUiState(
            movieModalUiModel = movieModalUiModel,
            myMovieRatedAndWantedItemUiModel = myMovieRatedAndWantedItemUiModel,
        ),
        content = {
            HomeDetailScreen(
                modifier = modifier,
                movies = movies,
                onImageClick = { item ->
                    coroutineScope.launch {
                        if (!modalSheetState.isVisible) {
                            viewModel.setModalItem(
                                MovieModalUiModel(
                                    id = item.id,
                                    title = item.title,
                                    adult = item.adult,
                                    backgroundImage = item.posterPath,
                                )
                            )
                            modalSheetState.show()
                        }
                    }
                },
            )
        },
        action = action,
        navigateToMovieDetail = navigateToMovieDetail,
    )
}

@Composable
fun HomeDetailScreen(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<MovieItem>,
    onImageClick: (item: MovieItem) -> Unit,
){
    PagingDynamicLazyVerticalGrid(
        cols = DEFAULT_COL,
        items = movies,
        content = { movie ->
            Movie(
                movie = movie,
                onImageClick = onImageClick
            )
        }
    )
}

@Composable
fun Movie(
    modifier: Modifier = Modifier,
    movie: MovieItem,
    onImageClick: (item: MovieItem) -> Unit,
) {
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
        modifier = modifier
            .aspectRatio(MOVIE_ASPECT_RATIO, true)
            .clickable {
                onImageClick(movie)
            }

    )
}