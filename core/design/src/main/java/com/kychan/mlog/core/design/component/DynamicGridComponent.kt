package com.kychan.mlog.core.design.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object DynamicGridComponent {
    const val DEFAULT_ROW_DYNAMIC_INDEX = 3
}

interface DynamicGridItem {
    val isRowDynamic: Boolean
    val isReverse: Boolean
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