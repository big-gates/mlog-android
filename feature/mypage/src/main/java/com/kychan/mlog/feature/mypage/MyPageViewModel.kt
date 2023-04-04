package com.kychan.mlog.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.observe.ObserveMyRatedMovie
import com.kychan.mlog.feature.mypage.model.MyMovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val observeMyRatedMovie: ObserveMyRatedMovie,
): ViewModel() {

    val myRatedMovies: StateFlow<List<MyMovieItem>> = observeMyRatedMovie()
        .map { movies ->
            movies.map {
                MyMovieItem.of(it)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )
}