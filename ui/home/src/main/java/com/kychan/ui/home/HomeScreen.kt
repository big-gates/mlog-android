package com.kychan.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.kychan.core.design.icon.MLogIcons
import com.kychan.core.design.theme.MovieRankBg
import com.kychan.core.design.theme.MovieRating
import com.kychan.ui.home.model.MovieCategory
import com.kychan.ui.home.model.MovieItem

val dummyMovieData = listOf<MovieItem>(
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
    MovieCategory("박스오피스 순위", dummyMovieData),
    MovieCategory("왓챠 영화 순위", dummyMovieData),
    MovieCategory("넷플릭스 영화 순위", dummyMovieData),
)

@Composable
@Preview
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
@Preview
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    viewModel.init()
    Column {
        HomeAppBar()
        LazyColumn(
            contentPadding = PaddingValues(vertical = 5.dp)
        ) {
            items(dummyMovieRankingsByCategory) { category ->
                MovieRankingsByCategory(category)
            }
        }
    }
}

@Composable
@Preview
fun MovieRankingsByCategory(
    category: MovieCategory = dummyMovieRankingsByCategory[0]
) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
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
            .width(100.dp)
            .padding(end = 7.dp)
    ) {
        Box {
            Image(
                painter = rememberImagePainter(
                    data = movie.image,
                    builder = {
                        crossfade(true)
                    }
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
            lineHeight = 17.sp

        )

        MovieRating(
            rating = movie.rating,
            modifier = Modifier.padding(top = 5.dp)
        )
    }
}

@Composable
@Preview
fun MovieRankBox(
    rank: String = "1",
    modifier: Modifier = Modifier
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
            text = "$rank",
            fontSize = 10.sp,
            color = Color.White,
        )
    }
}

@Composable
@Preview
fun MovieRating(
    rating: Float = 10f,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = "예상 ★ $rating",
            color = MovieRating,
            fontSize = 13.sp
        )
    }
}
