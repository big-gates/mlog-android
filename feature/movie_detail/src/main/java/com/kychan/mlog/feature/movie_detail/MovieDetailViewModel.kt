package com.kychan.mlog.feature.movie_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.observe.ObserveMovieDetail
import com.kychan.mlog.core.model.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeMovieDetail: ObserveMovieDetail,
) : ViewModel() {

    private val movieId: Int = savedStateHandle["movieId"]!!

}