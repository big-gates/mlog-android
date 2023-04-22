package com.kychan.mlog.feature.mypage

import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.observe.ObserveMyRatedMovie
import com.kychan.mlog.core.domain.observe.ObserveMyWantToWatchMovie
import com.kychan.mlog.core.domain.usecase.*
import com.kychan.mlog.feature.movie_modal.MovieModalBottomSheetViewModel
import com.kychan.mlog.feature.mypage.model.MyMovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val observeMyWantToWatchMovie: ObserveMyWantToWatchMovie,
    private val observeMyRatedMovie: ObserveMyRatedMovie,
    private val insertMyWantMovie: InsertMyWantMovie,
    private val deleteMyWantMovie: DeleteMyWantMovie,
    private val existToMyWantMovie: ExistToMyWantMovie,
    private val existToMyRatedMovie: ExistToMyRatedMovie,
) : MovieModalBottomSheetViewModel(
    insertMyWantMovie, deleteMyWantMovie, existToMyWantMovie, existToMyRatedMovie
) {

    val myWantToWatchMovies: StateFlow<List<MyMovieItem>> = observeMyWantToWatchMovie()
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