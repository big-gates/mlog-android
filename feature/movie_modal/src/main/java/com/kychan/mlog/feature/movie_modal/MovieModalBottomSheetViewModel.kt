package com.kychan.mlog.feature.movie_modal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.usecase.*
import com.kychan.mlog.core.model.Rated
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class MovieModalBottomSheetViewModel(
    private val insertMyWantMovie: InsertMyWantMovie,
    private val deleteMyWantMovie: DeleteMyWantMovie,
    private val existToMyWantMovie: ExistToMyWantMovie,
    private val existToMyRatedMovie: ExistToMyRatedMovie,
) : ViewModel() {

    val ratedMovieInfo: MutableStateFlow<Rated?> = MutableStateFlow(null)
    val isLikeMovie = MutableStateFlow(false)

    private var onShowModalItem: MovieModalTO = MovieModalTO()

    fun existToMyMovie(item: MovieModalTO) {
        viewModelScope.launch {
            ratedMovieInfo.value = existToMyRatedMovie.invoke(item.id)
            isLikeMovie.value = existToMyWantMovie.invoke(item.id) > 0
            onShowModalItem = item
        }
    }

    fun replaceRated(comment: String) {
        ratedMovieInfo.value = ratedMovieInfo.value?.copy(comment = comment)
    }
    fun replaceRated(rate: Float) {
        ratedMovieInfo.value = ratedMovieInfo.value?.copy(rated = rate)
    }

    fun insertOrDeleteMyWantMovie() {
        if (isLikeMovie.value) {
            deleteMyWantMovie(onShowModalItem)
        } else {
            insertMyWantMovie(onShowModalItem)
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