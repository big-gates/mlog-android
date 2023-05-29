package com.kychan.mlog.feature.movie_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.kychan.mlog.core.design.component.MovieInfoHeader
import com.kychan.mlog.core.design.component.MovieInfoRated
import com.kychan.mlog.core.design.component.MovieInfoStoryAndTags
import com.kychan.mlog.core.designsystem.BuildConfig

@Preview
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val uiModel: MovieDetailUiModel by viewModel.uiModel.collectAsState()
    val myMovieRatedAndWanted by viewModel.myMovieRatedAndWanted.collectAsState()

    val focusManager = LocalFocusManager.current

    Box {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            contentScale = ContentScale.FillHeight,
            model = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}w342${uiModel.posterPath}",
            contentDescription = "movie_detail_image"
        )
        Column {
            MovieInfoHeader(
                title = uiModel.title,
                isAdult = uiModel.adult,
                isLike = myMovieRatedAndWanted.isLike,
                onLikeClick = {
                    viewModel.insertOrDeleteMyWantMovie()
                },
            )
            MovieInfoRated(
                comment = myMovieRatedAndWanted.comment,
                rate = myMovieRatedAndWanted.rated,
                onTextChange = { comment, rating ->
                     viewModel.updateMyRatedMovie(comment, rating)
                },
                onRateChange = { comment, rating ->
                     viewModel.updateMyRatedMovie(comment, rating)
                },
                focusManager = focusManager,
            )
            MovieInfoStoryAndTags(
                story = uiModel.overview.orEmpty(),
                directer = "감독 없음",
                releaseDate = uiModel.releaseDate,
                tags = uiModel.genres.map { it.name },
            )
        }
    }
}