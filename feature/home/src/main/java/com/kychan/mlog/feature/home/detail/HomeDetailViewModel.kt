package com.kychan.mlog.feature.home.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kychan.mlog.core.domain.observe.ObserveMlogMovie
import com.kychan.mlog.core.domain.observe.ObserveNetflixMovie
import com.kychan.mlog.core.domain.observe.ObserveWatchaMovie
import com.kychan.mlog.core.model.WatchProvider
import com.kychan.mlog.feature.home.model.MovieItem
import com.kychan.mlog.feature.home.model.toView
import com.kychan.mlog.feature.home.navigation.HomeDetailArgs
import com.kychan.mlog.feature.movie_modal.MovieModalBottomSheetViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeMlogMovie: ObserveMlogMovie,
    private val observeNetflixMovie: ObserveNetflixMovie,
    private val observeWatchaMovie: ObserveWatchaMovie,
): MovieModalBottomSheetViewModel() {

    private val homeDetailArgs: HomeDetailArgs = HomeDetailArgs(savedStateHandle)

    val movies: StateFlow<PagingData<MovieItem>> = when(homeDetailArgs.watchProviderId){
        WatchProvider.MLOG_ID -> observeMlogMovie().cachedIn(viewModelScope)
        WatchProvider.NETFLIX_ID -> observeNetflixMovie().cachedIn(viewModelScope)
        WatchProvider.WATCHA_ID -> observeWatchaMovie().cachedIn(viewModelScope)
        else -> throw IllegalArgumentException("can't support watch provider id")
    }.map { paging ->
        paging.map { movie ->
            movie.toView(
                posterSize = POSTER_SIZE,
                watchProviderId = homeDetailArgs.watchProviderId
            )
        }
    }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PagingData.empty()
    )

    override fun onLikeClick() {
        insertOrDeleteMyWantMovie()
    }

    override fun onTextChange(comment: String) {
        replaceRated(comment = comment)
    }

    override fun onRateChange(rate: Float) {
        replaceRated(rate = rate)
    }

    companion object{
        const val POSTER_SIZE = "w342"
    }
}