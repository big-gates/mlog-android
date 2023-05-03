package com.kychan.mlog.feature.movie_modal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.usecase.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class MovieModalBottomSheetViewModel(
    private val insertMyWantMovie: InsertMyWantMovie,
    private val updateMyRatedMovie: UpdateMyRatedMovie,
    private val deleteMyWantMovie: DeleteMyWantMovie,
    private val existToMyWantMovie: ExistToMyWantMovie,
    private val existToMyRatedMovie: ExistToMyRatedMovie,
) : ViewModel() {

    val ratedMovieInfo: MutableStateFlow<RateItem> = MutableStateFlow(RateItem())
    val isLikeMovie = MutableStateFlow(false)

    private var onShowModalItem: MovieModalUiModel = MovieModalUiModel()

    fun existToMyMovie(item: MovieModalUiModel) {
        viewModelScope.launch {
            ratedMovieInfo.value = existToMyRatedMovie.invoke(item.id)?.toRateItem() ?: RateItem()
            isLikeMovie.value = existToMyWantMovie.invoke(item.id)
            onShowModalItem = item
        }
    }

    fun replaceRated(comment: String, rate: Float) {
        ratedMovieInfo.value = ratedMovieInfo.value.copy(comment = comment, rate = rate)
        updateMyRatedMovie(onShowModalItem.copy(comment = comment, rate = rate))
    }

    fun insertOrDeleteMyWantMovie() {
        if (isLikeMovie.value) {
            deleteMyWantMovie(onShowModalItem)
        } else {
            insertMyWantMovie(onShowModalItem)
        }
    }

    private fun updateMyRatedMovie(movieModalUiModel: MovieModalUiModel) {
        viewModelScope.launch {
            updateMyRatedMovie.invoke(
                myMovie = movieModalUiModel.toMyMovie(),
                rated = movieModalUiModel.toRated()
            )
        }
    }

    private fun insertMyWantMovie(movieModalUiModel: MovieModalUiModel) {
        viewModelScope.launch {
            insertMyWantMovie.invoke(
                myMovie = movieModalUiModel.toMyMovie(),
                wantToWatch = movieModalUiModel.toWantToWatch()
            )
            isLikeMovie.value = true
        }
    }

    private fun deleteMyWantMovie(movieModalUiModel: MovieModalUiModel) {
        viewModelScope.launch {
            deleteMyWantMovie.invoke(
                myMovie = movieModalUiModel.toMyMovie(),
                wantToWatch = movieModalUiModel.toWantToWatch()
            )
            isLikeMovie.value = false
        }
    }
}