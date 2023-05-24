package com.kychan.mlog.feature.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.observe.ObserveMovieDetail
import com.kychan.mlog.core.domain.observe.ObserveMyMovieRatedAndWanted
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.MyMovieRatedAndWanted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeMovieDetail: ObserveMovieDetail,
    private val observeMyMovieRatedAndWanted: ObserveMyMovieRatedAndWanted,
) : ViewModel() {

    private val movieId: Int = savedStateHandle["movieId"]!!

    private val _uiModel = MutableStateFlow(emptyMovieDetailUiModel())
    val uiModel: StateFlow<MovieDetailUiModel> = _uiModel

    val myMovieRatedAndWanted: StateFlow<MyMovieRatedAndWantedItemUiModel> = observeMyMovieRatedAndWanted(movieId)
        .map(MyMovieRatedAndWanted::toMyMovieRatedAndWantedItemUiModel)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MyMovieRatedAndWantedItemUiModel(0f, "", false)
        )

    init {
        viewModelScope.launch {
            getMovieDetail(movieId)
        }
    }
    private suspend fun getMovieDetail(movieId: Int) {
        val detail = observeMovieDetail.invoke(movieId, Language.KR, "")
        _uiModel.value = detail.toUiModel()
    }

}