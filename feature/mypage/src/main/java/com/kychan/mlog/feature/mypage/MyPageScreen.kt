package com.kychan.mlog.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.kychan.mlog.feature.movie_modal.ModalAction
import com.kychan.mlog.feature.movie_modal.MovieModalUiModel
import com.kychan.mlog.feature.movie_modal.MovieModalUiState
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
    pagerState: PagerState,
    onSortClick: () -> Unit,
    onClick: (item: MyMovieItem) -> Unit,
) {
    Column {
        val pages = listOf("평가한", "보고싶어요")
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
            Column {
                Row(modifier = Modifier.clickable { onSortClick() }) {
                    Image(
                        painter = painterResource(id = MLogIcons.Sort),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(Black),
                    )
                    Text(
                        text = "최근에 담은 순"
                    )
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

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun MyPageRoute(
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateToMovieDetail: (id: Int) -> Unit,
) {
    val myRatedMovies by viewModel.myRatedMovies.collectAsStateWithLifecycle()
    val myWantToWatchMovies by viewModel.myWantToWatchMovies.collectAsStateWithLifecycle()
    val movieModalUiModel by viewModel.movieModalUiModel.collectAsStateWithLifecycle()
    val myMovieRatedAndWantedItemUiModel by viewModel.myMovieRatedAndWantedItemUiModel.collectAsStateWithLifecycle()
    val action: ModalAction = viewModel

    val coroutineScope = rememberCoroutineScope()
    val movieModalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            it != ModalBottomSheetValue.Expanded
        },
        skipHalfExpanded = false
    )
    val sortSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            it != ModalBottomSheetValue.Expanded
        },
        skipHalfExpanded = false
    )
    val pagerState = rememberPagerState()

    Box(modifier = Modifier.fillMaxSize()) {
        MyPageScreen(
            myRatedMovies = myRatedMovies,
            myWantToWatchMovies = myWantToWatchMovies,
            pagerState = pagerState,
            onSortClick = {
                coroutineScope.launch {
                    sortSheetState.show()
                }
            },
            onClickMovieItem = { item ->
                coroutineScope.launch {
                    if (!movieModalSheetState.isVisible) {
                        viewModel.setModalItem(
                            MovieModalUiModel(
                                id = item.myMovieId,
                                title = item.title,
                                adult = item.adult,
                                backgroundImage = item.posterPath,
                            )
                        )
                        movieModalSheetState.show()
                    }
                }
            },
        )

        BottomSheetLayout(
            modalSheetState = movieModalSheetState,
            movieModalUiState = MovieModalUiState(
                movieModalUiModel = movieModalUiModel,
                myMovieRatedAndWantedItemUiModel = myMovieRatedAndWantedItemUiModel,
            ),
            action = action,
            navigateToMovieDetail = navigateToMovieDetail,
        )

        ModalBottomSheetLayout(
            sheetState = sortSheetState,
            sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            sheetContent = {
                StorageSortBottomSheetContent(
                    isRatePage = pagerState.currentPage == 0,
                    clickSortType = viewModel::setSort
                )
            },
            content = {}
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyPageScreen(
    myRatedMovies: List<MyMovieItem> = emptyList(),
    myWantToWatchMovies: List<MyMovieItem> = emptyList(),
    pagerState: PagerState,
    onSortClick: () -> Unit,
    onClickMovieItem: (MyMovieItem) -> Unit = {},
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            MyPageAppBar()
            MyPageView(
                myRatedMovies = myRatedMovies,
                myWantToWatchMovies = myWantToWatchMovies,
                pagerState = pagerState,
                onSortClick = onSortClick,
                onClick = { item ->
                    onClickMovieItem(item)
                }
            )
        }
    }
}