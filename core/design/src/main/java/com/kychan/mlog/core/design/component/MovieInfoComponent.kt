package com.kychan.mlog.core.design.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.kychan.mlog.core.design.theme.AlphaBlack80
import com.kychan.mlog.core.design.theme.Gray600
import com.kychan.mlog.core.design.theme.Gray800
import com.kychan.mlog.core.design.theme.Pink500
import com.kychan.mlog.core.design.theme.Purple200
import com.kychan.mlog.core.design.theme.Purple500
import com.kychan.mlog.core.design.theme.Purple700
import com.kychan.mlog.core.design.theme.White
import com.kychan.mlog.core.designsystem.R
import kotlinx.coroutines.delay

/*** 추후 아래 영화 정보에 관한 Component는
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
            .clip(RoundedCornerShape(10.dp))
            .background(AlphaBlack80)
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
                val paintColor = if (isLike) Color.Red else Color.White
                Icon(
                    painter = painterResource(id = paintId),
                    contentDescription = "like_icon_movie",
                    modifier = Modifier.fillMaxSize(),
                    tint = paintColor,
                )
            }
            if (isAdult) {
                Icon(
                    painter = painterResource(id = R.drawable.adult_movie),
                    contentDescription = "adult_movie",
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .width(28.dp)
                        .height(28.dp),
                    tint = White,
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
    onTextChange: (String) -> Unit,
    onRateChange: (Float) -> Unit,
    focusManager: FocusManager,
) {
    var changeComment by remember { mutableStateOf(comment) }
    var changeRate by remember { mutableStateOf(0f) }

    // 각 영화 Item 마다 다른 데이터로 초기화 하기 위함.
    LaunchedEffect(rate) {
        changeRate = rate
    }
    LaunchedEffect(comment) {
        changeComment = comment
    }
    LaunchedEffect(changeComment) { // comment 변경 시 마다 delay 후 방출
        delay(500L)
        if (changeComment != comment) onTextChange(changeComment)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 41.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AlphaBlack80)
            .padding(horizontal = 10.dp, vertical = 12.dp),
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
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedIndicatorColor = Color.Gray
                ),
                textStyle = TextStyle.Default.copy(color = White, fontSize = 14.sp),
                value = changeComment,
                singleLine = true,
                onValueChange = { changeComment = it },
            )
            Icon(
                painter = painterResource(id = R.drawable.comment_write),
                contentDescription = "comment_write",
                modifier = Modifier
                    .padding(start = 11.dp)
                    .width(24.dp)
                    .height(24.dp),
                tint = Gray600,
            )
        }
        RatingBar(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            value = changeRate,
            config = RatingBarConfig()
                .stepSize(StepSize.HALF)
                .size(32.dp)
                .style(RatingBarStyle.HighLighted),
            onValueChange = { changeRate = it },
            onRatingChanged = {
                /*
                 onRatingChanged는 드래그 하는 도중이 아닌 드래그가 시작되는 시점, 끝나는 시점에 호출이 됨
                 영화 별점 설정을 0점으로 했을 때 -1이라는 플래그값으로 삭제를 의미 영화를 저장할 때는 0점으로 저장.
                 */
                if (it <= 0f) onRateChange(-1f) else onRateChange(it)
            }
        )

    }
}

@Composable
fun MovieInfoStoryAndTags(
    story: String,
    directer: String,
    releaseDate: String,
    tags: List<String>,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, top = 41.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AlphaBlack80)
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
                text = story.ifEmpty { "해당 영화는 한국어 번역이 지원 되지 않습니다." },
                color = White,
                fontSize = 14.sp,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.weight(1f))
            MovieInfoDirect(directer, releaseDate)
            MovieInfoTags(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                tags = tags
            )
        }
    }
}

@Composable
fun MovieInfoDirect(
    directer: String,
    releaseDate: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        horizontalAlignment = Alignment.End,
    ) {
        /**
         * detail api에서 감독 정보가 없음
         * 추 후 감독 정보 불러오기 추가
         */
//        Text(
//            text = "감독 : $directer",
//            color = White,
//            fontSize = 14.sp,
//            maxLines = 2,
//            overflow = TextOverflow.Ellipsis,
//        )

        Text(
            modifier = Modifier.padding(top = 11.dp),
            text = "개봉일자 : $releaseDate",
            color = White,
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun MovieInfoTags(
    modifier: Modifier = Modifier,
    tags: List<String>,
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(tags){
            MovieTag(tagName = it)
            Spacer(modifier = Modifier.width(13.dp))
        }
    }
}


@Composable
fun MovieTag(
    tagName: String,
    fontSize: TextUnit = 10.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    val colors = listOf(Purple200, Pink500, Purple500, Gray800, Purple700)

    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(colors[(0..3).random()])
            .padding(vertical = 5.dp, horizontal = 15.dp),
        textAlign = TextAlign.Center,
        text = if (tagName.length >= 5) tagName.breakDown(2) else tagName,
        fontSize = fontSize,
        fontWeight = fontWeight,
        overflow = overflow,
        color = White
    )
}

private fun String.breakDown(breakDownIndex: Int) = "${this.substring(0, breakDownIndex)}\n${this.substring(breakDownIndex)}"

@Preview
@Composable
fun MovieComponentPreview() {
    Column() {
        MovieTag(
            tagName = "드라마",
        )
        MovieInfoHeader(
            title = "샹치와 텐링즈의 전설은 아마 나의 것이지 않을까",
            isAdult = true,
            isLike = true,
            onLikeClick = {},
        )
        MovieInfoRated(
            comment = "좀 많이 아쉬운 영화..",
            rate = 4.0f,
            onTextChange = {},
            onRateChange = {},
            focusManager = LocalFocusManager.current,
        )
        MovieInfoStoryAndTags(
            story = "빚 떼려다 혹 붙였다!\n" +
                "책입지고 받아 키워 드립니다!\n" +
                "\n" +
                "1993년 인천 거칠고 까칠한 사채업자 두석과\n" +
                "종배는 떼인 돈 받으러 갔다가 얼떨결에 9살 승이를 \n" +
                "담보로 맡게 된다 ...",
            directer = "곽하민",
            releaseDate = "2018년 6월 12일",
            tags = listOf("액션","코미디","판타지","이건일곱글자지","블록버스터"),
        )
    }
}