package com.kychan.mlog.core.design.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.kychan.mlog.core.design.theme.Black
import com.kychan.mlog.core.design.theme.White
import com.kychan.mlog.core.designsystem.R

/***
 *
 * 추후 아래 영화 정보에 관한 Component는
 * 다른 Screen에서 사용할 떄에 함수 인자에 modifier를 넣을 예정!
 */
@Composable
fun MovieInfoHeader(
    title: String,
    isAdult: Boolean,
    isLike: Boolean,
    onLikeClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 65.dp)
            .background(Color.Black)
            .padding(horizontal = 10.dp, vertical = 12.dp),
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            fontSize = 24.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = White,
        )
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            IconButton(
                modifier = Modifier
                    .width(28.dp)
                    .height(28.dp),
                onClick = {
                    onLikeClick()
                }
            ) {
                val paintId = if (isLike) R.drawable.ic_favorite_fill else R.drawable.ic_favorite_border
                Icon(
                    painter = painterResource(id = paintId),
                    contentDescription = "like_icon_movie",
                    modifier = Modifier.fillMaxSize()
                )
            }
            if (isAdult) {
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieInfoRated(
    comment: String,
    rate: Float,
    onTextChange: (String, Float) -> Unit,
    onRateChange: (String, Float) -> Unit,
    focusManager: FocusManager,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 41.dp)
            .background(color = Black)
            .padding(horizontal = 10.dp, vertical = 12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "내 점수는요?",
                color = White,
                fontWeight = FontWeight.Bold,
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
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged {
                            if (it.isFocused) {
                                // focused
                                Log.d("TAG", "focused")
                            } else {
                                // not focused
                                Log.d("TAG", "not focused")
                            }
                        },
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Black,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    ),
                    textStyle = TextStyle.Default.copy(color = Color.White, fontSize = 14.sp),
                    value = comment,
                    singleLine = true,
                    onValueChange = { onTextChange(it, rate) },
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
                value = rate,
                config = RatingBarConfig()
                    .stepSize(StepSize.HALF)
                    .size(32.dp)
                    .style(RatingBarStyle.HighLighted),
                onValueChange = { onRateChange(comment, it) },
                onRatingChanged = {
                    Log.d("TAG", "onRatingChanged: $it")
                }
            )

        }
    }
}

@Preview
@Composable
fun MovieInfoStoryAndTags() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, top = 41.dp)
            .background(color = Black)
            .padding(start = 11.dp, end = 18.dp, top = 20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "줄거리",
                color = White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )

            Text(
                modifier = Modifier
                    .padding(top = 17.dp, end = 38.dp),
                text = "빚 떼려다 혹 붙였다!\n" +
                    "책입지고 받아 키워 드립니다!\n" +
                    "\n" +
                    "1993년 인천 거칠고 까칠한 사채업자 두석과\n" +
                    "종배는 떼인 돈 받으러 갔다가 얼떨결에 9살 승이를 \n" +
                    "담보로 맡게 된다 ...\n",
                color = White,
                fontSize = 14.sp,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.weight(1f))
            MovieInfoDirect()
            MovieInfoTags()
        }
    }
}

@Preview
@Composable
fun MovieInfoDirect() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        horizontalAlignment = Alignment.End,
    ) {
        Text(
            text = "감독 : 곽하민",
            color = White,
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            modifier = Modifier.padding(top = 11.dp),
            text = "2018년 6월 12일",
            color = White,
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun MovieInfoTags() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 8.dp, end = 7.dp)
            .background(color = Black)
            .padding(horizontal = 11.dp, vertical = 9.dp)
    ) {
        for (i in 0..3) {
            MovieTag(tagName = "드라마", size = 42.dp)
            Divider(
                color = Color.Transparent,
                modifier = Modifier.width(13.dp)
            )
        }
    }
}


@Composable
fun MovieTag(
    tagName: String,
    rotate: Float = -45f,
    size: Dp = 60.dp,
    fontSize: TextUnit = 10.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    overflow: TextOverflow = TextOverflow.Ellipsis,
){
    val widthRatio = 0.64
    val heightRatio = 0.5
    val offsetRatio = 0.07

    Box(
        modifier = Modifier.rotate(rotate),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(size)
                .scale(1.4f)
            ,
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.tag),
            contentDescription = "",
        )
        Text(
            modifier = Modifier
                .widthIn(max = (size.value * widthRatio).dp)
                .heightIn(max = (size.value * heightRatio).dp)
                .offset(-(size.value * offsetRatio).dp, (0).dp)
                .align(Alignment.Center)
            ,
            textAlign = TextAlign.Center,
            text = if(tagName.length >=5) tagName.breakDown(2) else tagName,
            fontSize = fontSize,
            fontWeight = fontWeight,
            overflow = overflow
        )
    }
}

private fun String.breakDown(breakDownIndex: Int) = "${this.substring(0,breakDownIndex)}\n${this.substring(breakDownIndex)}"

@Preview
@Composable
fun MovieTagPreview(){
    MovieTag(
        tagName = "드라마",
    )
}