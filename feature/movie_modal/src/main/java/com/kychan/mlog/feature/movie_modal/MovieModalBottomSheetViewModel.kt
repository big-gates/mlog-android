package com.kychan.mlog.feature.movie_modal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.observe.ObserveMyMovieRatedAndWanted
import com.kychan.mlog.core.domain.usecase.*
import com.kychan.mlog.core.model.MyMovieRatedAndWanted
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class MovieModalBottomSheetViewModel(
    private val insertMyWantMovie: InsertMyWantMovie,
    private val updateMyRatedMovie: UpdateMyRatedMovie,
    private val deleteMyWantMovie: DeleteMyWantMovie,
    private val observeMyMovieRatedAndWanted: ObserveMyMovieRatedAndWanted,
) : ViewModel() {

    val movieModalUiModel: MutableStateFlow<MovieModalUiModel> = MutableStateFlow(MovieModalUiModel())

    val myMovieRatedAndWantedItemUiModel: StateFlow<MyMovieRatedAndWantedItemUiModel> = movieModalUiModel.map {
        observeMyMovieRatedAndWanted(it.id)
    }.flatMapLatest { it }
    .map(MyMovieRatedAndWanted::toMyMovieRatedAndWantedItemUiModel)
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MyMovieRatedAndWantedItemUiModel(0f, "", false)
    )
    fun setModalItem(item: MovieModalUiModel) {
        viewModelScope.launch {
            movieModalUiModel.value = item
        }
    }

    fun replaceRated(comment: String, rate: Float) {
        updateMyRatedMovie(movieModalUiModel.value.copy(comment = comment, rate = rate))
    }

    fun insertOrDeleteMyWantMovie() {
        if (myMovieRatedAndWantedItemUiModel.value.isLike) {
            deleteMyWantMovie(movieModalUiModel.value)
        } else {
            insertMyWantMovie(movieModalUiModel.value)
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
        }
    }

    private fun deleteMyWantMovie(movieModalUiModel: MovieModalUiModel) {
        viewModelScope.launch {
            deleteMyWantMovie.invoke(
                myMovie = movieModalUiModel.toMyMovie(),
                wantToWatch = movieModalUiModel.toWantToWatch()
            )
        }
    }
}
data class MyMovieRatedAndWantedItemUiModel(
    val rated: Float,
    val comment: String,
    val isLike: Boolean,
)

fun MyMovieRatedAndWanted.toMyMovieRatedAndWantedItemUiModel() = MyMovieRatedAndWantedItemUiModel(
    rated = rated,
    comment = comment,
    isLike = isLike,
)