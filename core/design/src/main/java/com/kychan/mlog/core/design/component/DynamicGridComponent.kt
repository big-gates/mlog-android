package com.kychan.mlog.core.design.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.kychan.mlog.core.design.util.maxScrollFlingBehavior

object DynamicGridComponent {
    const val DEFAULT_COL = 3
    const val DEFAULT_ROW_DYNAMIC_INDEX = 16
}

interface DynamicGridItem {
    val isRowDynamic: Boolean
    val isReverse: Boolean
}

fun LazyGridState.isScrolledToEnd(endOfPaginationReached: Boolean?):Boolean {
    return  endOfPaginationReached?: false && (layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1)
}

fun <T : Any> LazyGridScope.items(
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
        flingBehavior = maxScrollFlingBehavior(),
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
            item?.let { content(item) }
        }
    }
}

@Composable
fun <T: DynamicGridItem> DynamicLazyVerticalGrid(
    modifier: Modifier = Modifier,
    cols: Int,
    height: Double,
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
    items: List<T?>,
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
    items: List<T?>,
    content: @Composable (item: T) -> Unit,
) {
    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(2/3f)) {
            items[0]?.let { content(it) }
        }
        Spacer(modifier = Modifier.width(3.dp))
        Column(modifier = Modifier.weight(1/3f)) {
            Row(modifier = Modifier.weight(0.5f)) { items[1]?.let { content(it) } }
            Spacer(modifier = Modifier.height(3.dp))
            Row(modifier = Modifier.weight(0.5f)) { items[2]?.let { content(it) } }
        }
    }
}

@Composable
private fun <T: DynamicGridItem>ReverseSpanGridItem(
    modifier: Modifier = Modifier,
    items: List<T?>,
    content: @Composable (item: T) -> Unit,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(1/3f)) {
            Row(modifier = Modifier.weight(0.5f)) { items[0]?.let { content(it) } }
            Spacer(modifier = Modifier.height(3.dp))
            Row(modifier = Modifier.weight(0.5f)) { items[1]?.let { content(it) } }
        }
        Spacer(modifier = Modifier.width(3.dp))
        Column(modifier = Modifier.weight(2/3f)) {
            items[2]?.let { content(it) }
        }
    }
}