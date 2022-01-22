package com.kychan.mlog.presentation.main.mypage

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.kychan.mlog.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movieList: LiveData<PagedList<MyMovieItem>> by lazy {
        movieRepository.getMovieAll()
    }

    fun deleteMovie(link: String) {
        Thread {
            movieRepository.deleteMovie(link)
        }.start()
    }

    fun updateMovie(rating: Float, link: String) {
        Thread {
            movieRepository.updateMovie(rating, link)
        }.start()
    }
}