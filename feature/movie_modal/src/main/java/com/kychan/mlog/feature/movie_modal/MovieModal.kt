package com.kychan.mlog.feature.movie_modal

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    modalSheetState: ModalBottomSheetState,
    movieModalUiState: MovieModalUiState,
    action: ModalAction,
    content: @Composable () -> Unit = {},
    navigateToMovieDetail: (id: Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val isSheetFullScreen by remember { mutableStateOf(false) }
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp
    val focusManager = LocalFocusManager.current

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch {
            modalSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
        sheetContent = {
            MovieModalBottomSheetLayout(movieModalUiState, action, focusManager, navigateToMovieDetail)
        },
        content = content
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
    action: ModalAction,
    focusManager: FocusManager,
    navigateToMovieDetail: (id: Int) -> Unit,
) {
    val comment = remember { mutableStateOf(movieModalUiState.myMovieRatedAndWantedItemUiModel.comment) }
    val rate = remember { mutableStateOf(movieModalUiState.myMovieRatedAndWantedItemUiModel.rated) }
    val debounceTime = 500L // 딜레이 시간 설정 (밀리초 단위)

    LaunchedEffect(movieModalUiState) { // flow 값 적용 mutableStateOf로하면 변경값 안먹힘
        comment.value = movieModalUiState.myMovieRatedAndWantedItemUiModel.comment
        rate.value = movieModalUiState.myMovieRatedAndWantedItemUiModel.rated
    }
    LaunchedEffect(comment.value) { // comment 변경 시 마다 debounce
        snapshotFlow { comment.value }
            .debounce(debounceTime)
            .collect {
                if (it != movieModalUiState.myMovieRatedAndWantedItemUiModel.comment)
                    action.onTextChange(it)
            }
    }
    LaunchedEffect(rate.value) { // rate 변경 시 마다 debounce
        snapshotFlow { rate.value }
            .debounce(debounceTime)
            .collect {
                if (it != movieModalUiState.myMovieRatedAndWantedItemUiModel.rated)
                    action.onRateChange(it)
            }
    }

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
                isLike = movieModalUiState.myMovieRatedAndWantedItemUiModel.isLike,
                onLikeClick = { action.onLikeClick() },
            )
            MovieInfoRated(
                comment = comment.value,
                rate = rate.value,
                onTextChange = { comment.value = it },
                onRateChange = { rate.value = it },
                focusManager = focusManager,
            )
            MovieInfoTags(
                tags = listOf("드라마")
            )
        }
    }
}