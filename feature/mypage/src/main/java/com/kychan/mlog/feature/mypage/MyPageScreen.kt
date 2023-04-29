package com.kychan.mlog.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.accompanist.pager.*
import com.kychan.mlog.core.design.icon.MLogIcons
import com.kychan.mlog.feature.mypage.model.MyMovieItem
import com.kychan.mlog.core.design.theme.Black
import com.kychan.mlog.feature.movie_modal.BottomSheetLayout
import com.kychan.mlog.feature.movie_modal.MovieModalEvent
import com.kychan.mlog.feature.movie_modal.MovieModalState
import com.kychan.mlog.feature.movie_modal.MovieModalTO
import kotlinx.coroutines.launch


@Composable
fun MyPageAppBar() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "보관함")
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyPageView(
    myRatedMovies: List<MyMovieItem>,
    myWantToWatchMovies: List<MyMovieItem>,
    onClick: (item: MyMovieItem) -> Unit,
) {
    Column {
        val pages = listOf("평가한", "보고싶어요")
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        TabRow(
            backgroundColor = MaterialTheme.colors.background,
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                )
            }
        }

        Row {
            Image(
                painter = painterResource(id = MLogIcons.Sort),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Black),
            )
            Text(
                text = "정렬 버튼 들어가는 자리"
            )
        }
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = pages.size,
            state = pagerState
        ) { page ->
            val itemList = when (pagerState.currentPage) {
                0 -> myRatedMovies
                1 -> myWantToWatchMovies
                else -> emptyList()
            }
            PhotoGrid(
                photos = itemList,
                onClick = { item ->
                    onClick(item)
                }
            )
        }
    }
}

@Composable
fun PhotoGrid(
    photos: List<MyMovieItem>,
    onClick: (item: MyMovieItem) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(photos.size) { index ->
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(0.667f)
                    .background(color = Color.Green)
                    .clickable {
                        onClick(photos[index])
                    },
                model = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}w342${photos[index].posterPath}",
                contentDescription = "my_movie_image"
            )
        }
    }
}

@Composable
fun MyPageRoute(
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val myRatedMovies by viewModel.myRatedMovies.collectAsStateWithLifecycle()
    val myWantToWatchMovies by viewModel.myWantToWatchMovies.collectAsStateWithLifecycle()
    val isRatedState by viewModel.ratedMovieInfo.collectAsStateWithLifecycle()
    val isLikeState by viewModel.isLikeMovie.collectAsStateWithLifecycle()

    MyPageScreen(
        myRatedMovies = myRatedMovies,
        myWantToWatchMovies = myWantToWatchMovies,
        movieModalItemState = MovieModalState(
            isRatedState = isRatedState,
            isLikeState = isLikeState,
            onShowModal = { item ->
                viewModel.existToMyMovie(item)
            },
            modalEvent = MovieModalEvent(
                onLikeClick = {
                    viewModel.insertOrDeleteMyWantMovie()
                },
                onTextChange = {
                    viewModel.replaceRated(it)
                },
                onRateChange = {
                    viewModel.replaceRated(it)
                },
            )
        ),
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyPageScreen(
    myRatedMovies: List<MyMovieItem> = emptyList(),
    myWantToWatchMovies: List<MyMovieItem> = emptyList(),
    movieModalItemState: MovieModalState,
) {
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            it != ModalBottomSheetValue.Expanded
        },
        skipHalfExpanded = false
    )
    val movieModalTOState: MutableState<MovieModalTO> = remember { mutableStateOf(MovieModalTO()) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            MyPageAppBar()
            MyPageView(
                myRatedMovies = myRatedMovies,
                myWantToWatchMovies = myWantToWatchMovies,
                onClick = { item ->
                    coroutineScope.launch {
                        if (modalSheetState.isVisible) {
                            modalSheetState.hide()
                        } else {
                            movieModalTOState.value = MovieModalTO(
                                id = item.myMovieId,
                                title = item.title,
                                adult = item.adult,
                                isLike = movieModalItemState.isLikeState,
                                comment = movieModalItemState.isRatedState.comment,
                                rate = movieModalItemState.isRatedState.rate,
                                backgroundImage = item.posterPath,
                                tags = emptyList()
                            )
                            modalSheetState.show()
                            movieModalItemState.onShowModal(movieModalTOState.value)
                        }
                    }
                }
            )
        }
        BottomSheetLayout(
            modalSheetState = modalSheetState,
            movieModalTO = movieModalTOState.value.copy(
                isLike = movieModalItemState.isLikeState,
                comment = movieModalItemState.isRatedState.comment,
                rate = movieModalItemState.isRatedState.rate
            ),
            movieModalEvent = movieModalItemState.modalEvent,
        )
    }
}