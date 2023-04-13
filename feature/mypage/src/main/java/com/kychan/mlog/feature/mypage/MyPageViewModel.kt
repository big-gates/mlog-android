package com.kychan.mlog.feature.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.design.component.movie_modal.MovieModalTO
import com.kychan.mlog.core.domain.observe.ObserveMyRatedMovie
import com.kychan.mlog.core.domain.observe.ObserveMyWantToWatchMovie
import com.kychan.mlog.core.domain.usecase.ExistToMyWantMovie
import com.kychan.mlog.core.domain.usecase.InsertMyWantMovie
import com.kychan.mlog.core.model.MyMovie
import com.kychan.mlog.core.model.WantToWatch
import com.kychan.mlog.core.model.WatchProvider
import com.kychan.mlog.feature.mypage.model.MyMovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val observeMyWantToWatchMovie: ObserveMyWantToWatchMovie,
    private val observeMyRatedMovie: ObserveMyRatedMovie,
    private val insertMyWantMovie: InsertMyWantMovie,
    private val existToMyWantMovie: ExistToMyWantMovie
) : ViewModel() {

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

    val isLikeMovie = MutableStateFlow(false)
    fun existToMyWantMovie(itemId: Int) {
        viewModelScope.launch {
            isLikeMovie.value = existToMyWantMovie.invoke(itemId) > 0
        }
    }

    fun insertMyWantMovie(movieModalTO: MovieModalTO) {
        viewModelScope.launch {
            insertMyWantMovie.invoke(
                myMovie = movieModalTO.toMyMovie(),
                wantToWatch = movieModalTO.toWantToWatch()
            )
            isLikeMovie.value = true
        }
    }

    private fun MovieModalTO.toMyMovie() = MyMovie(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backgroundImage,
        originalTitle = "originalTitle",
        posterPath = this.backgroundImage,
        title = this.title,
        voteAverage = 3.0,
        watchProviders = WatchProvider.None,
        rank = 1,
    )

    private fun MovieModalTO.toWantToWatch() = WantToWatch(
        id = this.id,
        myMovieId = this.id
    )
}