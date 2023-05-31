package com.kychan.mlog.feature.movie_modal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.observe.ObserveMyMovieRatedAndWanted
import com.kychan.mlog.core.domain.usecase.*
import com.kychan.mlog.core.model.MyMovieRatedAndWanted
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ModalAction {
    fun onLikeClick()
    fun onTextChange(comment: String)
    fun onRateChange(rate: Float)
}

abstract class MovieModalBottomSheetViewModel : ViewModel(), ModalAction {
    @Inject lateinit var insertMyWantMovie: InsertMyWantMovie
    @Inject lateinit var updateMyRatedMovie: UpdateMyRatedMovie
    @Inject lateinit var deleteMyWantMovie: DeleteMyWantMovie
    @Inject lateinit var observeMyMovieRatedAndWanted: ObserveMyMovieRatedAndWanted

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

    fun replaceRated(
        comment: String = myMovieRatedAndWantedItemUiModel.value.comment,
        rate: Float = myMovieRatedAndWantedItemUiModel.value.rated
    ) {
        val model = movieModalUiModel.value.copy(comment = comment, rate = rate)
        viewModelScope.launch {
            updateMyRatedMovie.invoke(
                myMovie = model.toMyMovie(),
                rated = model.toRated()
            )
        }
    }

    fun insertOrDeleteMyWantMovie() {
        if (myMovieRatedAndWantedItemUiModel.value.isLike) {
            deleteMyWantMovie(movieModalUiModel.value)
        } else {
            insertMyWantMovie(movieModalUiModel.value)
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