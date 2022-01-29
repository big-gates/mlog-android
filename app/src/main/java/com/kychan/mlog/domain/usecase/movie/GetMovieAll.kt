package com.kychan.mlog.domain.usecase.movie

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.kychan.mlog.domain.repository.MovieRepository
import com.kychan.mlog.presentation.main.mypage.MyMovieItem
import javax.inject.Inject

class GetMovieAll @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(): LiveData<PagedList<MyMovieItem>> {
        return movieRepository.getMovieAll()
    }
}