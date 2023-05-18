package com.kychan.mlog.feature.movie_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.kychan.mlog.core.design.component.MovieInfoHeader
import com.kychan.mlog.core.design.component.MovieInfoRated
import com.kychan.mlog.core.design.component.MovieInfoStoryAndTags
import com.kychan.mlog.core.designsystem.BuildConfig
import com.kychan.mlog.feature.movie_modal.MovieModalEvent
import com.kychan.mlog.feature.movie_modal.MovieModalUiModel

@Preview
@Composable
fun MovieDetailScreen(

) {
    val movieModalUiModel: MovieModalUiModel = MovieModalUiModel()
    val modalEvent: MovieModalEvent = MovieModalEvent({},{},{_,_ ->},{_,_ ->})
    val focusManager = LocalFocusManager.current
    Box {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            contentScale = ContentScale.FillHeight,
            model = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}w342${movieModalUiModel.backgroundImage}",
            contentDescription = "movie_detail_image"
        )
        Column {
            MovieInfoHeader(
                title = movieModalUiModel.title,
                isAdult = movieModalUiModel.adult,
                isLike = movieModalUiModel.isLike,
                onLikeClick = modalEvent.onLikeClick,
            )
            MovieInfoRated(
                comment = movieModalUiModel.comment,
                rate = movieModalUiModel.rate,
                onTextChange = modalEvent.onTextChange,
                onRateChange = modalEvent.onRateChange,
                focusManager = focusManager,
            )
            MovieInfoStoryAndTags()
        }
    }
}