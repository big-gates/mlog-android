package com.kychan.mlog.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.kychan.mlog.data.local.model.MovieEntity
import com.kychan.mlog.presentation.main.mypage.MyMovieItem
import com.kychan.mlog.presentation.main.search.SearchMovieItem

interface MovieRepository {
    fun invalidateDataSource()

    fun setSearchKeyword(keyword: String)

    fun getItemTotal(): LiveData<Int>

    fun getSearchMovieList(): LiveData<PagedList<SearchMovieItem>>

    fun getMovieAll(): LiveData<PagedList<MyMovieItem>>

    fun insertMovie(movieEntity: MovieEntity)

    fun deleteMovie(link: String)

    fun updateMovie(evaluation: Float, link: String)
}
