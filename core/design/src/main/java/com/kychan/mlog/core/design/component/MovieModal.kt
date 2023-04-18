package com.kychan.mlog.core.design.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.kychan.mlog.core.design.component.movie_modal.MovieModalTO
import com.kychan.mlog.core.designsystem.BuildConfig
import com.kychan.mlog.core.designsystem.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    modalSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    ),
    movieModalTO: MovieModalTO? = null,
    onLikeClick: () -> Unit,
) {
    val isSheetFullScreen by remember { mutableStateOf(false) }
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp
    val modifier = Modifier.fillMaxSize()

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
        sheetContent = {
            MovieModalBottomSheetLayout(modifier, movieModalTO, onLikeClick)
        },
        content = {}
    )

}

@Preview
@Composable
fun MovieModalBottomSheetLayout(
    modifier: Modifier = Modifier.fillMaxSize(),
    movieModalTO: MovieModalTO? = null,
    onLikeClick: () -> Unit = {},
) {
    val initialRating = 0f
    var rating: Float by remember { mutableStateOf(initialRating) }
    var text: String by remember { mutableStateOf("") }

    Box {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            contentScale = ContentScale.FillHeight,
            model = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}w342${movieModalTO?.backgroundImage.orEmpty()}",
            contentDescription = "movie_modal_image"
        )
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, top = 65.dp)
                    .background(Color.Black)
                    .padding(horizontal = 10.dp, vertical = 12.dp),
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = movieModalTO?.title.orEmpty(),
                    fontSize = 24.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                )
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    IconButton(
                        modifier = Modifier
                            .width(28.dp)
                            .height(28.dp),
                        onClick = {
                            Log.d("TAG", "클릭했습니다")
                            onLikeClick()
                        }
                    ) {
                        val paintId = if (movieModalTO?.isLike == true) R.drawable.ic_favorite_fill else R.drawable.ic_favorite_border
                        Icon(
                            painter = painterResource(id = paintId),
                            contentDescription = "like_icon_movie",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    if (movieModalTO?.adult == true) {
                        Icon(
                            painter = painterResource(id = R.drawable.adult_movie),
                            contentDescription = "adult_movie",
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .width(28.dp)
                                .height(28.dp)
                        )
                    }
                }
//                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, top = 41.dp)
                    .background(color = Color.Black)
                    .padding(horizontal = 10.dp, vertical = 12.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = "내 점수는요?",
                        color = Color.White,
                        fontSize = 15.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Row(
                        modifier = Modifier
                            .padding(start = 53.dp, end = 19.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Black,
                                unfocusedIndicatorColor = Color.Gray,
                                focusedIndicatorColor = Color.Gray
                            ),
                            textStyle = TextStyle.Default.copy(color = Color.White, fontSize = 14.sp),
                            value = movieModalTO?.comment.orEmpty(),
                            singleLine = true,
                            onValueChange = { text = it },
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.comment_write),
                            contentDescription = "comment_write",
                            modifier = Modifier
                                .padding(start = 11.dp)
                                .width(24.dp)
                                .height(24.dp)
                        )
                    }
                    RatingBar(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally),
                        value = movieModalTO?.rate ?: rating,
                        config = RatingBarConfig()
                            .stepSize(StepSize.HALF)
                            .size(32.dp)
                            .style(RatingBarStyle.HighLighted),
                        onValueChange = {
                            rating = it
                        },
                        onRatingChanged = {
                            Log.d("TAG", "onRatingChanged: $it")
                        }
                    )

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 8.dp, end = 7.dp)
                    .background(color = Color.Black)
                    .padding(horizontal = 11.dp, vertical = 9.dp)
            ) {
                for (i in 0..3) {
                    Text(
                        text = "드라마",
                        color = Color.White,
                        modifier = Modifier.padding(start = 13.dp)
                    )
                }
            }
        }
    }
}