package com.kychan.mlog.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.kychan.mlog.core.design.component.BottomSheetLayout
import com.kychan.mlog.core.design.icon.MLogIcons
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
    onClick: () -> Unit,
) {
    Column {
        val pages = listOf("평가한", "보고싶어요")
        val pagerState = rememberPagerState()
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

        Row {
            Image(
                painter = painterResource(id = MLogIcons.Sort),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.Black),
            )
            Text(
                text = "정렬 버튼 들어가는 자리"
            )
        }
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = pages.size,
            state = pagerState
        ) { page ->
            val itemList = when (pagerState.currentPage){
                0 -> list
                1 -> list2
                else -> list
            }
            PhotoGrid(
                photos = itemList,
                onClick = {
                    onClick()
                }
            )
        }
    }
}

val list = listOf<String>("1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3")
val list2 = listOf<String>("1","2","3","1")
@Composable
fun PhotoGrid(
    photos: List<String>,
    onClick: () -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(photos.size) { photo ->
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Green)
                    .height(210.dp)
                    .clickable {
                        onClick()
                    },
                text = photo.toString(),
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun MyPageScreen() {
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            // Expanded 라면 내비게이션 이동 로직 확인해보기
            it != ModalBottomSheetValue.Expanded
        },
        skipHalfExpanded = false
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            MyPageAppBar()
            MyPageView(
                onClick = {
                    coroutineScope.launch {
                        if (modalSheetState.isVisible)
                            modalSheetState.hide()
                        else
                            modalSheetState.show()
                    }
                }
            )
        }
        BottomSheetLayout(modalSheetState = modalSheetState)
    }
}