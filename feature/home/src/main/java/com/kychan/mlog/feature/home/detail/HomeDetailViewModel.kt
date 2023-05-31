package com.kychan.mlog.feature.home.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kychan.mlog.core.design.component.DynamicGridComponent.DEFAULT_ROW_DYNAMIC_INDEX
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

    val movies: StateFlow<PagingData<MovieItem>> = when(homeDetailArgs.watchProvider){
        WatchProvider.None -> observeMlogMovie().cachedIn(viewModelScope)
        WatchProvider.Netflix -> observeNetflixMovie().cachedIn(viewModelScope)
        WatchProvider.Watcha -> observeWatchaMovie().cachedIn(viewModelScope)
    }.map { paging ->
        paging.map { movie ->
            movie.toView(
                posterSize = POSTER_SIZE,
                isRowDynamic = movie.rank % (DEFAULT_ROW_DYNAMIC_INDEX) == 0,
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