package com.kychan.mlog.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kychan.mlog.core.design.icon.MLogIcons
import com.kychan.mlog.core.design.theme.MovieRankBg
import com.kychan.mlog.core.design.theme.MovieRating
import com.kychan.mlog.feature.home.model.MovieCategory
import com.kychan.mlog.feature.home.model.MovieItem

val dummyMovieData = listOf(
    MovieItem(
        "https://image.tmdb.org/t/p/original/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        "1",
        10f,
        "파이트 클럽"
    ),
    MovieItem(
        "https://image.tmdb.org/t/p/original/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        "1",
        10f,
        "파이트 클럽"
    ),
    MovieItem(
        "https://image.tmdb.org/t/p/original/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        "1",
        10f,
        "파이트 클럽"
    ),
    MovieItem(
        "https://image.tmdb.org/t/p/original/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        "1",
        10f,
        "파이트 클럽"
    ),
    MovieItem(
        "https://image.tmdb.org/t/p/original/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        "1",
        10f,
        "파이트 클럽"
    ),
    MovieItem(
        "https://image.tmdb.org/t/p/original/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        "1",
        10f,
        "파이트 클럽"
    )
)

val dummyMovieRankingsByCategory = listOf(
    MovieCategory("Mlog 추천 Pick", dummyMovieData),
    MovieCategory("Mlog가 추천하는 Netfilx", dummyMovieData),
    MovieCategory("Mlog가 추천하는 Watcha", dummyMovieData),
)

@Composable
fun HomeAppBar() {
    TopAppBar(
        backgroundColor = Color.White
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = MLogIcons.Logo),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )

            Image(
                painter = painterResource(id = MLogIcons.Search),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.Black),
            )
        }
    }
}

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val movieRankingsByCategory by viewModel.movieRankingsByCategory.collectAsStateWithLifecycle()

    HomeScreen(
        movieRankingsByCategory = movieRankingsByCategory
    )
}

@Composable
fun HomeScreen(
    movieRankingsByCategory: List<MovieCategory> = listOf()
) {
    Column {
        HomeAppBar()
        LazyColumn(
            contentPadding = PaddingValues(vertical = 5.dp),
        ) {
            items(movieRankingsByCategory) { category ->
                MovieRankingsByCategory(category)
            }
        }
    }
}

@Composable
fun MovieRankingsByCategory(
    category: MovieCategory
) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .heightIn(min = 280.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = category.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(id = MLogIcons.RightArrow),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(12.dp)
                    .height(12.dp)
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        ) {
            items(category.movieItems) { movie ->
                Movie(movie = movie)
            }
        }
    }
}

@Composable
fun Movie(
    movie: MovieItem,
) {
    Column(
        modifier = Modifier
            .width(110.dp)
            .padding(end = 7.dp)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = movie.image).apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build()
                ),
                contentDescription = "movie poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
            )

            MovieRankBox(
                rank = movie.rank,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 2.5.dp, bottom = 2.5.dp)
            )
        }

        Text(
            text = movie.title,
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 5.dp),
            lineHeight = 17.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        MovieRating(
            rating = movie.rating,
            modifier = Modifier.padding(top = 5.dp)
        )
    }
}

@Composable
fun MovieRankBox(
    modifier: Modifier = Modifier,
    rank: String
) {
    Row(
        modifier = modifier
            .width(20.dp)
            .height(20.dp)
            .background(
                color = MovieRankBg,
                shape = RoundedCornerShape(5.dp)
            ),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = rank,
            fontSize = 10.sp,
            color = Color.White,
        )
    }
}

@Composable
fun MovieRating(
    modifier: Modifier = Modifier,
    rating: Float
) {
    Row(modifier = modifier) {
        Text(
            text = "예상 ★ $rating",
            color = MovieRating,
            fontSize = 13.sp
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview(){
    HomeScreen(
        movieRankingsByCategory = dummyMovieRankingsByCategory
    )
}

@Composable
@Preview
fun MovieRankingByCategoryPreview(){
    MovieRankingsByCategory(category = dummyMovieRankingsByCategory[0])
}

@Composable
@Preview
fun MoviePreview(){
    Movie(movie = dummyMovieData[0])
}

@Composable
@Preview
fun MovieRankBoxPreview(){
    MovieRankBox(rank = "1")
}

@Composable
@Preview
fun MovieRatingPreview(){
    MovieRating(rating = 10f)
}

@Composable
@Preview
fun HomeAppBarPreview(){
    HomeAppBar()
}