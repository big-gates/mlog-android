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

    private var onShowModalItem: MovieModalTO = MovieModalTO()

    fun existToMyMovie(item: MovieModalTO) {
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

    private fun updateMyRatedMovie(movieModalTO: MovieModalTO) {
        viewModelScope.launch {
            updateMyRatedMovie.invoke(
                myMovie = movieModalTO.toMyMovie(),
                rated = movieModalTO.toRated()
            )
        }
    }

    private fun insertMyWantMovie(movieModalTO: MovieModalTO) {
        viewModelScope.launch {
            insertMyWantMovie.invoke(
                myMovie = movieModalTO.toMyMovie(),
                wantToWatch = movieModalTO.toWantToWatch()
            )
            isLikeMovie.value = true
        }
    }

    private fun deleteMyWantMovie(movieModalTO: MovieModalTO) {
        viewModelScope.launch {
            deleteMyWantMovie.invoke(
                myMovie = movieModalTO.toMyMovie(),
                wantToWatch = movieModalTO.toWantToWatch()
            )
            isLikeMovie.value = false
        }
    }
}