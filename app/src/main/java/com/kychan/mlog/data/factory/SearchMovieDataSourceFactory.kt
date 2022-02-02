package com.kychan.mlog.data.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.kychan.mlog.data.local.dao.MovieDao
import com.kychan.mlog.data.remote.NaverApi
import com.kychan.mlog.data.repository.SearchMovieDataSource
import com.kychan.mlog.presentation.main.search.SearchMovieItem
import javax.inject.Inject

class SearchMovieDataSourceFactory @Inject constructor(
    private val naverApi: NaverApi,
    private val movieDao: MovieDao,
) : DataSource.Factory<Int, SearchMovieItem>() {

    val liveData = MutableLiveData<SearchMovieDataSource>()
    private var searchKeyword: String? = null

    fun setSearchKeyword(keyword: String) {
        searchKeyword = keyword
    }

    override fun create(): DataSource<Int, SearchMovieItem> {
        val source = SearchMovieDataSource(naverApi)
        source.setSearchKeyword(searchKeyword.orEmpty())

        liveData.postValue(source)
        return source.map {
            val searchMovieItem = it.toSearchMovieItem()
            Thread {
                searchMovieItem.isMyMovie = movieDao.getMovieId().contains(it.link)
            }.start()
            searchMovieItem
        }
    }

    companion object {
        private const val PAGE_SIZE = 10

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }
}
