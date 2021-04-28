package com.kychan.mlog.presentation.main.mypage

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.kychan.mlog.model.database.MovieDao
import com.kychan.mlog.presentation.main.search.SearchMovieItem
import com.kychan.mlog.repository.SearchMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyMovieViewModel @Inject constructor(
    private val movieDao: MovieDao,
    private val searchMovieRepository: SearchMovieRepository
) : ViewModel() {

    val movieList: LiveData<PagedList<SearchMovieItem>> by lazy {
        searchMovieRepository.getMovieAll()
    }

    fun deleteMovie(link: String) {
        Thread {
            movieDao.delete(link)
        }.start()
    }
}