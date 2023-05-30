package com.kychan.mlog.feature.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.observe.ObserveMovieDetail
import com.kychan.mlog.core.domain.observe.ObserveMyMovieRatedAndWanted
import com.kychan.mlog.core.domain.usecase.DeleteMyWantMovie
import com.kychan.mlog.core.domain.usecase.InsertMyWantMovie
import com.kychan.mlog.core.domain.usecase.UpdateMyRatedMovie
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.MyMovieRatedAndWanted
import com.kychan.mlog.core.model.Rated
import com.kychan.mlog.core.model.WantToWatch
import com.kychan.mlog.feature.movie_modal.MyMovieRatedAndWantedItemUiModel
import com.kychan.mlog.feature.movie_modal.toMyMovieRatedAndWantedItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeMovieDetail: ObserveMovieDetail,
    private val observeMyMovieRatedAndWanted: ObserveMyMovieRatedAndWanted,
    private val insertMyWantMovie: InsertMyWantMovie,
    private val deleteMyWantMovie: DeleteMyWantMovie,
    private val updateMyRatedMovie: UpdateMyRatedMovie,
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

    fun insertOrDeleteMyWantMovie() {
        if (myMovieRatedAndWanted.value.isLike) {
            deleteMyWantMovie()
        } else {
            insertMyWantMovie()
        }
    }
    private fun insertMyWantMovie() {
        viewModelScope.launch {
            insertMyWantMovie.invoke(
                myMovie = uiModel.value.toMyMovie(),
                wantToWatch = WantToWatch(uiModel.value.id, uiModel.value.id)
            )
        }
    }
    private fun deleteMyWantMovie() {
        viewModelScope.launch {
            deleteMyWantMovie.invoke(
                myMovie = uiModel.value.toMyMovie(),
                wantToWatch = WantToWatch(uiModel.value.id, uiModel.value.id)
            )
        }
    }

    fun updateMyRatedMovie(comment: String, rate: Float) {
        viewModelScope.launch {
            updateMyRatedMovie.invoke(
                myMovie = uiModel.value.toMyMovie(),
                rated = Rated(
                    id = uiModel.value.id,
                    myMovieId = uiModel.value.id,
                    rated = rate,
                    comment = comment,
                ),
            )
        }
    }

}