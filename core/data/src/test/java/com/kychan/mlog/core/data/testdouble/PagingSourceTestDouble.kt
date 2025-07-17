package com.kychan.mlog.core.data.testdouble

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class FlowPagingSource<T : Any>(
    private val dataFlow: Flow<List<T>> // Flow<List<T>> 데이터 소스
) : PagingSource<Int, T>() {
    private var allData: List<T> = emptyList()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        if (allData.isEmpty()) {
            allData = dataFlow.first()
        }

        val position = params.key ?: 0
        val loadSize = params.loadSize
        val endPosition = minOf(position + loadSize, allData.size)
        val sublist = allData.subList(position, endPosition)

        return LoadResult.Page(
            data = sublist,
            prevKey = if (position == 0) null else position - loadSize,
            nextKey = if (position == allData.size) null else endPosition
        )
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }
}