package com.kychan.mlog.feature.movie_modal

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kychan.mlog.core.design.component.MovieInfoHeader
import com.kychan.mlog.core.design.component.MovieInfoRated
import com.kychan.mlog.core.design.component.MovieInfoTags
import com.kychan.mlog.core.designsystem.BuildConfig

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    modalSheetState: ModalBottomSheetState,
    movieModalUiState: MovieModalUiState,
    navigateToMovieDetail: (id: Int) -> Unit,
) {
    val isSheetFullScreen by remember { mutableStateOf(false) }
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp
    val focusManager = LocalFocusManager.current

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
        sheetContent = {
            MovieModalBottomSheetLayout(movieModalUiState, focusManager, navigateToMovieDetail)
        },
        content = {}
    )

    LaunchedEffect(modalSheetState.isVisible) {
        if (!modalSheetState.isVisible) {
            focusManager.clearFocus()
        }
    }

}

@Composable
fun MovieModalBottomSheetLayout(
    movieModalUiState: MovieModalUiState,
    focusManager: FocusManager,
    navigateToMovieDetail: (id: Int) -> Unit,
) {
    Box {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { navigateToMovieDetail(movieModalUiState.movieModalUiModel.id) }
                ),
            contentScale = ContentScale.FillHeight,
            model = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}w342${movieModalUiState.movieModalUiModel.backgroundImage}",
            contentDescription = "movie_modal_image"
        )
        Column {
            MovieInfoHeader(
                title = movieModalUiState.movieModalUiModel.title,
                isAdult = movieModalUiState.movieModalUiModel.adult,
                isLike = movieModalUiState.isLikeState,
                onLikeClick = movieModalUiState.modalEvent.onLikeClick,
            )
            MovieInfoRated(
                comment = movieModalUiState.isRatedState.comment,
                rate = movieModalUiState.isRatedState.rate,
                onTextChange = movieModalUiState.modalEvent.onTextChange,
                onRateChange = movieModalUiState.modalEvent.onRateChange,
                focusManager = focusManager,
            )
            MovieInfoTags(
                tags = listOf("드라마")
            )
        }
    }
}