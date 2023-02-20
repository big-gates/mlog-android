package com.kychan.mlog.core.design.component

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import kotlin.math.abs

object DynamicGridComponent {
    const val DEFAULT_COL = 3
    const val DEFAULT_ROW_DYNAMIC_INDEX = 2

    /**
     * limitVelocity 의 값이 클 수록 스크롤이 빨라집니다.
     * Custom GridView가 생각보다 무거워 스크롤이 너무 빠르면 컴포넌트를 생성하면서 Main Thread(UI Thread)를 많이 잡고있어
     * 의도치 않는 현상이 발생하여 스크롤 속도를 조절하는 maxScrollFlingBehavior를 만들었습니다.
     *
     * @param limitVelocity 값이 크면 스크롤 속도가 빨라집니다.
     * **/
    @Composable
    fun maxScrollFlingBehavior(limitVelocity: Float = 15000F): FlingBehavior {
        val flingSpec = rememberSplineBasedDecay<Float>()
        return remember(flingSpec) {
            ScrollSpeedFlingBehavior(flingSpec, limitVelocity)
        }
    }
}

interface DynamicGridItem {
    val isRowDynamic: Boolean
    val isReverse: Boolean
}

fun LazyGridState.isScrolledToEnd(endOfPaginationReached: Boolean?):Boolean {
    return  endOfPaginationReached?: false && (layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1)
}

fun <T: DynamicGridItem> LazyGridScope.items(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    span: ((item: T) -> GridItemSpan)? = null,
    contentType: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyGridItemScope.(value: T?, index: Int) -> Unit
) {
    items(
        count = items.itemCount,
        key = if (key != null) { index: Int -> items[index]?.let(key) ?: index } else null,
        span = if (span == null) null else { index ->
            val item = items.peek(index)
            if (item == null) {
                GridItemSpan(1)
            } else {
                span(item)
            }
        },
        contentType = if (contentType == null) {
            { null }
        } else { index ->
            val item = items.peek(index)
            if (item == null) {
                null
            } else {
                contentType(item)
            }
        }
    ) { index ->
        itemContent(items[index], index)
    }
}
@Composable
fun <T: DynamicGridItem> PagingDynamicLazyVerticalGrid(
    modifier: Modifier = Modifier,
    cols: Int,
    height: Int,
    items: LazyPagingItems<T>,
    content: @Composable (item: T) -> Unit,
    showEndOfPaginationReached: () -> Unit = {},
    showError: () -> Unit = {},
) {
    val lazyGridState = rememberLazyGridState()
    var isFirstRowDynamic by remember { mutableStateOf(true) }

    var isShowEndOfPaginationReached by remember { mutableStateOf(false) }
    val endOfListReached by remember {
        derivedStateOf {
            lazyGridState.isScrolledToEnd(items.loadState.append.endOfPaginationReached)
        }
    }

    LaunchedEffect(endOfListReached) {
        if(endOfListReached && !isShowEndOfPaginationReached){
            showEndOfPaginationReached()
            isShowEndOfPaginationReached = true
        }
    }

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(cols),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(3.dp),
        flingBehavior = DynamicGridComponent.maxScrollFlingBehavior(),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ){

        items(
            items = items,
            span = { item ->
                if(item.isRowDynamic && isFirstRowDynamic){
                    GridItemSpan(cols)
                }else{
                    GridItemSpan(1)
                }
            }
        ){ item, index ->

            item?.let {
                val contentModifier = if(item.isRowDynamic){
                    Modifier.height(height.dp * 2)
                } else {
                    Modifier.height(height.dp)
                }
                Column(
                    modifier = contentModifier
                ) {
                    content(item)
                }
            }
        }
    }
}

@Composable
fun <T: DynamicGridItem> DynamicLazyVerticalGrid(
    modifier: Modifier = Modifier,
    cols: Int,
    height: Int,
    items: List<T>,
    dynamicGridContent: @Composable (item: T) -> Unit,
    normalGridContent: @Composable (item: T) -> Unit,
){
    val lazyGridState = rememberLazyGridState()
    var isFirstRowDynamic by remember { mutableStateOf(true) }

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(cols),
        modifier = modifier.fillMaxWidth(),
    ){

        items.forEachIndexed { index, item ->
            if(item.isRowDynamic){
                if(!isFirstRowDynamic) return@forEachIndexed
                val itemList = mutableListOf<T>()
                repeat(cols){
                    if(index + it >= items.size) return@repeat
                    itemList.add(items[index+it])
                }

                item(span = { GridItemSpan(cols) }){
                    SpanGrid(
                        modifier = modifier.height((height * 2).dp),
                        items = itemList,
                        reverse = item.isReverse
                    ){ item ->
                        dynamicGridContent(item)
                    }
                }
                isFirstRowDynamic = false
            }else{
                item(span = { GridItemSpan(1) }){
                    normalGridContent(item)
                }
                isFirstRowDynamic = true
            }
        }
    }
}

@Composable
private fun <T: DynamicGridItem>SpanGrid(
    modifier: Modifier = Modifier,
    items: List<T>,
    reverse: Boolean,
    content: @Composable (item: T) -> Unit
){
    if(reverse){
        ReverseSpanGridItem(
            modifier = modifier,
            items = items,
        ) { item ->
            content(item)
        }
    }else{
        NestedSpanGridItem(
            modifier = modifier,
            items = items,
        ) { item ->
            content(item)
        }
    }
}

@Composable
private fun <T: DynamicGridItem>NestedSpanGridItem(
    modifier: Modifier = Modifier,
    items: List<T>,
    content: @Composable (item: T) -> Unit,
) {
    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(2/3f)) {
            content(items[0])
        }
        Column(modifier = Modifier.weight(1/3f)) {
            Row(modifier = Modifier.weight(0.5f)) { content(items[1]) }
            Row(modifier = Modifier.weight(0.5f)) { content(items[2]) }
        }
    }
}

@Composable
private fun <T: DynamicGridItem>ReverseSpanGridItem(
    modifier: Modifier = Modifier,
    items: List<T>,
    content: @Composable (item: T) -> Unit,
) {
    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(1/3f)) {
            Row(modifier = Modifier.weight(0.5f)) { content(items[1]) }
            Row(modifier = Modifier.weight(0.5f)) { content(items[2]) }
        }
        Column(modifier = Modifier.weight(2/3f)) {
            content(items[0])
        }
    }
}

private class ScrollSpeedFlingBehavior(
    private val flingDecay: DecayAnimationSpec<Float>,
    private val limitVelocity: Float,
) : FlingBehavior {
    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        // 매우 빠른 스크롤을 막기 위한 것
        val newVelocity = if (initialVelocity > 0F) minOf(initialVelocity, limitVelocity)
        else maxOf(initialVelocity, -limitVelocity)

        return if (abs(newVelocity) > 1f) {
            var velocityLeft = newVelocity
            var lastValue = 0f
            AnimationState(
                initialValue = 0f,
                initialVelocity = newVelocity,
            ).animateDecay(flingDecay) {
                val delta = value - lastValue
                val consumed = scrollBy(delta)
                lastValue = value
                velocityLeft = this.velocity
                // 반올림 오류를 방지하고 사용하지 않는 항목이 있으면 중지합니다
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
            velocityLeft
        } else newVelocity
    }
}