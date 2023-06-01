package com.kychan.mlog.feature.movie_modal

import android.annotation.SuppressLint
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
import com.kychan.mlog.core.designsystem.BuildConfig

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    modalSheetState: ModalBottomSheetState,
    movieModalUiModel: MovieModalUiModel,
    movieModalEvent: MovieModalEvent,
) {
    val isSheetFullScreen by remember { mutableStateOf(false) }
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp
    val focusManager = LocalFocusManager.current

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
        sheetContent = {
            MovieModalBottomSheetLayout(movieModalUiModel, movieModalEvent, focusManager)
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
    movieModalUiModel: MovieModalUiModel,
    modalEvent: MovieModalEvent,
    focusManager: FocusManager,
) {
    Box {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            contentScale = ContentScale.FillHeight,
            model = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}w342${movieModalUiModel.backgroundImage}",
            contentDescription = "movie_modal_image"
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
            MovieInfoRated()
        }
    }
}