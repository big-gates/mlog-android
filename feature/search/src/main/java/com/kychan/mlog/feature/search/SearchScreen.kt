package com.kychan.mlog.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.kychan.mlog.core.design.icon.MLogIcons
import com.kychan.mlog.core.design.theme.Gray600
import com.kychan.mlog.feature.search.model.MovieItem

@Composable
fun SearchRouter(){
    SearchScreen()
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
){
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val movies by viewModel.movies.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxHeight()) {
        SearchBar(
            text = searchText,
            onTextChange = viewModel::updateSearchText
        )
        SearchView(
            text = searchText,
            movies = movies
        )
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun SearchBar(
    text: String,
    onTextChange: (text: String) -> Unit = {}
){
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 10.dp,
                vertical = 10.dp
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
            onDone = { keyboardController?.hide() }
        ),
        singleLine = true,
        shape = RoundedCornerShape(50),
    )
}

@Composable
fun SearchView(
    text: String,
    movies: List<MovieItem> = listOf()
) {
    if(text.isEmpty()){
        EmptySearchView()
    }else{
        SearchResultView(movies)
    }
}

@Composable
fun SearchResultView(
    movies: List<MovieItem>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ){
        items(
            items = movies,
            key = { it.id }
        ){
            Movie(it)
        }
    }
}

@Composable
fun Movie(
    movie: MovieItem
){
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
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(MOVIE_ASPECT_RATIO, true)
    )
}

@Composable
fun EmptySearchView(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "영화를 검색해 보세요"
        )
    }
}