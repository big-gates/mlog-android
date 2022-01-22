package com.kychan.mlog.presentation.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.kychan.mlog.data.local.model.MovieEntity
import com.kychan.mlog.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    fun getMovieList(): LiveData<PagedList<SearchMovieItem>> =
        movieRepository.getSearchMovieList()

    val total: LiveData<Int> =
        movieRepository.getItemTotal()

    fun setKeyword(searchKeyword: String) {
        movieRepository.setSearchKeyword(searchKeyword)
    }

    fun insertMovie(item: SearchMovieItem) {
        Thread {
            movieRepository.insertMovie(MovieEntity.of(item))
        }.start()
    }

    fun deleteMovie(link: String) {
        Thread {
            movieRepository.deleteMovie(link)
        }.start()
    }

}