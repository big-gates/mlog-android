package com.kychan.mlog.feature.movie_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.observe.ObserveMovieDetail
import com.kychan.mlog.core.model.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val observeMovieDetail: ObserveMovieDetail,
) : ViewModel() {

    fun fetchInfoMovie(movieId: Int) {
        viewModelScope.launch {
            Log.d("TAG", observeMovieDetail.invoke(movieId, Language.KR, "").toString())
        }
    }

}