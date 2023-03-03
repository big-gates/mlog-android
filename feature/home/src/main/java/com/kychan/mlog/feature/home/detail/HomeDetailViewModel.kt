package com.kychan.mlog.feature.home.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.observe.ObserveMoviePopular
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.feature.home.navigation.HomeDetailArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeMoviePopular: ObserveMoviePopular,
): ViewModel() {

    private val homeDetailArgs: HomeDetailArgs = HomeDetailArgs(savedStateHandle)

    val movies: StateFlow<List<Movie>> = observeMoviePopular(ObserveMoviePopular.Params(homeDetailArgs.watchProvider))
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )
}