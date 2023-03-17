package com.kychan.mlog.core.design.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.kychan.mlog.core.designsystem.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    modalSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
) {
    var isSheetFullScreen by remember { mutableStateOf(false) }
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp
    val modifier = Modifier.fillMaxSize()

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
        sheetContent = {
            MovieModalBottomSheetLayout(modifier)
        },
        content = {}
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MovieModalBottomSheetLayout(
    modifier: Modifier = Modifier.fillMaxSize(),
) {
    val initialRating = 2.5f
    var rating: Float by remember { mutableStateOf(initialRating) }

    Column(
        modifier = modifier.background(Color.Yellow)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp, top = 65.dp)
                .background(color = Color.Red)
                .padding(horizontal = 10.dp, vertical = 12.dp),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "영화제목텍스트만약에",
                fontSize = 24.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Column(
                modifier = Modifier
                    .background(Color.Blue),
                horizontalAlignment = Alignment.End,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_favorite_border),
                    contentDescription = "unlike_movie",
                    modifier = Modifier
                        .width(28.dp)
                        .height(28.dp)
                )
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp, top = 41.dp)
                .background(color = Color.Red)
                .padding(horizontal = 10.dp, vertical = 12.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(Color.Blue)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "내 점수는요?",
                    fontSize = 24.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    modifier = Modifier
                        .padding(start = 53.dp, end = 19.dp)
                        .background(color = Color.Yellow)
                        .align(alignment = Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        modifier = Modifier.background(color = Color.Transparent),
                        value = "안녕하세요 곽하민 입니다.",
                        onValueChange = { },
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
                    value = rating,
                    config = RatingBarConfig().size(32.dp)
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
                .padding(top = 16.dp)
                .background(color = Color.Red)
                .padding(horizontal = 11.dp, vertical = 9.dp)
        ) {
            for (i in 0..3) {
                Text(
                    text = "드라마",
                    modifier = Modifier.padding(start = 13.dp)
                )
            }
        }
    }
}