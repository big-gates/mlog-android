package com.kychan.mlog.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.kychan.mlog.core.design.border.bottomBorder
import com.kychan.mlog.core.design.icon.MLogIcons
import com.kychan.mlog.core.design.theme.Gray400
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
    val recentSearchList by viewModel.recentSearchList.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxHeight()) {
        SearchBar(
            text = searchText,
            onTextChange = viewModel::updateSearchText
        )
        SearchView(
            text = searchText,
            recentSearchList = recentSearchList,
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
            onDone = { keyboardController?.hide() }
        ),
        singleLine = true,
        shape = RoundedCornerShape(50),
    )
}

@Composable
fun SearchView(
    text: String,
    recentSearchList: List<String> = listOf(),
    movies: List<MovieItem> = listOf(),
) {
    if(text.isEmpty()){
        RecentSearchListView(
            recentSearchList = recentSearchList
        )
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
fun RecentSearchListView(
    recentSearchList: List<String>,
) {

    if(recentSearchList.isNotEmpty()){
        RecentSearchHeader()
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.Center,
    ){
        items(recentSearchList){
            RecentSearch(title = it)
        }
    }
}

@Composable
fun RecentSearchHeader(){
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
            fontWeight = FontWeight.Bold
        )

        Text(text = "모두 삭제")

    }
}

@Composable
fun RecentSearch(
    title: String
){
    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = title,
        fontSize = 16.sp
    )
}