package com.kychan.mlog.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kychan.mlog.core.domain.observe.ObserveMlogMovie
import com.kychan.mlog.core.domain.observe.ObserveNetflixMovie
import com.kychan.mlog.core.domain.observe.ObserveWatchaMovie
import com.kychan.mlog.feature.home.model.MovieItem
import com.kychan.mlog.feature.home.model.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeMlogMovie: ObserveMlogMovie,
    private val observeNetflixMovie: ObserveNetflixMovie,
    private val observeWatchaMovie: ObserveWatchaMovie,
): ViewModel() {

    val mLogMovieItem: Flow<PagingData<MovieItem>> = observeMlogMovie()
        .map { paging ->
            paging.map { movie ->
                movie.toView(posterSize = POSTER_SIZE)
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PagingData.empty()
        )

    val netflixMovieItem: Flow<PagingData<MovieItem>> = observeNetflixMovie()
        .map { paging ->
            paging.map { movie ->
                movie.toView(posterSize = POSTER_SIZE)
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PagingData.empty()
        )

    val watchaMovieitem: Flow<PagingData<MovieItem>> = observeWatchaMovie()
        .map { paging ->
            paging.map { movie ->
                movie.toView(posterSize = POSTER_SIZE)
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PagingData.empty()
        )

    companion object{
        const val POSTER_SIZE = "w154"
        const val MLOG_RECOMMENDATION = "Mlog 추천 Pick"
        const val NETFLIX_RECOMMENDATION = "Mlog가 추천하는 Netflix"
        const val WATCHA_RECOMMENDATION = "Mlog가 추천하는 Watcha"
    }
}