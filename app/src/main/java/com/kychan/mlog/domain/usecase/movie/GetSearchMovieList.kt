package com.kychan.mlog.domain.usecase.movie

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.kychan.mlog.domain.repository.MovieRepository
import com.kychan.mlog.presentation.main.search.SearchMovieItem
import javax.inject.Inject

class GetSearchMovieList @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(): LiveData<PagedList<SearchMovieItem>> {
        return movieRepository.getSearchMovieList()
    }
}
