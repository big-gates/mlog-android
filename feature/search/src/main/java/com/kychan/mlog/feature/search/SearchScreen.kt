package com.kychan.mlog.feature.search

import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.kychan.mlog.core.design.border.bottomBorder
import com.kychan.mlog.core.design.component.items
import com.kychan.mlog.core.design.icon.MLogIcons
import com.kychan.mlog.core.design.icon.MLogIcons.Logo
import com.kychan.mlog.core.design.theme.*
import com.kychan.mlog.core.design.util.conditional
import com.kychan.mlog.feature.movie_modal.BottomSheetLayout
import com.kychan.mlog.feature.movie_modal.ModalAction
import com.kychan.mlog.feature.movie_modal.MovieModalUiModel
import com.kychan.mlog.feature.movie_modal.MovieModalUiState
import com.kychan.mlog.feature.search.model.MovieItem
import com.kychan.mlog.feature.search.model.RecentSearchView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchRouter(
    viewModel: SearchViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigateToMovieDetail: (id: Int) -> Unit,
) {
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
            SearchScreen(
                viewModel = viewModel,
                onImageClick = { item ->
                    coroutineScope.launch {
                        if (!modalSheetState.isVisible) {
                            viewModel.setModalItem(
                                MovieModalUiModel(
                                    id = item.id,
                                    title = item.title,
                                    adult = item.adult,
                                    backgroundImage = item.posterPath,
                                    genres = item.genres.map { it.kr }
                                )
                            )
                            modalSheetState.show()
                        }
                    }
                },
                onBackClick = onBackClick
            )
        },
        action = action,
        navigateToMovieDetail = navigateToMovieDetail,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    onImageClick: (item: MovieItem) -> Unit,
    onBackClick: () -> Unit,
){
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val recentSearchList by viewModel.recentSearchList.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler {
        if(searchText.isEmpty()) onBackClick()
        else viewModel.updateSearchText("")
    }

    Column(modifier = modifier.fillMaxHeight()) {
        SearchBar(
            text = searchText,
            onTextChange = viewModel::updateSearchText,
            search = viewModel::search,
            keyboardHide = { keyboardController?.hide() }
        )
        SearchView(
            text = searchText,
            recentSearchList = recentSearchList,
            movies = movies,
            deleteAll = viewModel::deleteAll,
            delete = viewModel::delete,
            onItemClick = viewModel::updateSearchText,
            onImageClick = onImageClick,
            keyboardHide = { keyboardController?.hide() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    text: String,
    onTextChange: (text: String) -> Unit = {},
    search: () -> Unit = {},
    keyboardHide: () -> Unit,
){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .bottomBorder(1.dp, Gray400)
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 10.dp,
                bottom = 15.dp
            ),
        value = text,
        leadingIcon = {
            Image(
                painter = painterResource(id = MLogIcons.Search),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Gray600),
            )
        },
        onValueChange = { onTextChange(it) },
        placeholder = {
            Text(text = "검색")
        },
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardHide()
                search()
            }
        ),
        singleLine = true,
        shape = Shapes.large,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onSurface,
            containerColor = Color.Transparent,
        ),
    )
}

@Composable
fun SearchView(
    text: String,
    recentSearchList: List<RecentSearchView> = listOf(),
    movies: LazyPagingItems<MovieItem>,
    deleteAll: () -> Unit,
    delete: (id: Int) -> Unit,
    onItemClick: (text: String) -> Unit,
    onImageClick: (item: MovieItem) -> Unit,
    keyboardHide: () -> Unit
) {
    if(text.isEmpty()){
        RecentSearchListView(
            recentSearchList = recentSearchList,
            deleteAll = deleteAll,
            delete = delete,
            onItemClick = onItemClick,
        )
    }else{
        SearchResultView(
            movies = movies,
            onImageClick = onImageClick,
            keyboardHide = keyboardHide,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchResultView(
    movies: LazyPagingItems<MovieItem>,
    onImageClick: (item: MovieItem) -> Unit,
    keyboardHide: () -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .pointerInteropFilter { motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> { keyboardHide() }
                }
                false
            },
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ){
        items(
            items = movies,
        ){ item, _ ->
            item?.let { Movie(it, onImageClick) }
        }
    }
}

@Composable
fun Movie(
    movie: MovieItem,
    onImageClick: (item: MovieItem) -> Unit,
){
    var isError by remember {
        mutableStateOf(false)
    }

    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest
                .Builder(LocalContext.current)
                .data(data = movie.image)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                })
                .error(Logo)
                .listener(
                    onSuccess = { _,_ -> isError = false },
                    onError = { _,_ -> isError = true }
                )
                .diskCachePolicy(CachePolicy.ENABLED)
                .build()
        ),
        contentDescription = "movie poster",
        contentScale = if(isError) ContentScale.Inside else ContentScale.Crop,
        modifier = Modifier
            .clip(Shapes.medium)
            .aspectRatio(MOVIE_ASPECT_RATIO, true)
            .conditional(isError) {
                border(width = 1.dp, Gray400, Shapes.medium)
            }
            .clickable {
                onImageClick(movie)
            },
    )
}

@Composable
fun RecentSearchListView(
    recentSearchList: List<RecentSearchView>,
    deleteAll: () -> Unit,
    delete: (id: Int) -> Unit,
    onItemClick: (text: String) -> Unit,
) {

    if(recentSearchList.isNotEmpty()){
        RecentSearchHeader(
            deleteAll = deleteAll
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.Center,
    ){
        items(recentSearchList){
            RecentSearch(
                recentSearchView = it,
                delete = delete,
                onItemClick = onItemClick,
            )
        }
    }
}

@Composable
fun RecentSearchHeader(
    deleteAll: () -> Unit = {},
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "최근 검색어",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground,
        )

        Text(
            modifier = Modifier.clickable { deleteAll() },
            text = "모두 삭제",
            color = MaterialTheme.colors.onBackground,
        )

    }
}

@Composable
fun RecentSearch(
    recentSearchView: RecentSearchView,
    delete: (id: Int) -> Unit,
    onItemClick: (text: String) -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(recentSearchView.text) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = recentSearchView.text,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onBackground,
        )

        Icon(
            modifier = Modifier.clickable { delete(recentSearchView.id) },
            imageVector = MLogIcons.Close,
            contentDescription = null,
            tint = Pink500
        )
    }
}