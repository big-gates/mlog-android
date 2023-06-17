package com.kychan.mlog.feature.mypage

import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.observe.ObserveMyRatedMovie
import com.kychan.mlog.core.domain.observe.ObserveMyWantToWatchMovie
import com.kychan.mlog.feature.movie_modal.MovieModalBottomSheetViewModel
import com.kychan.mlog.feature.mypage.model.MyMovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val observeMyWantToWatchMovie: ObserveMyWantToWatchMovie,
    private val observeMyRatedMovie: ObserveMyRatedMovie,
) : MovieModalBottomSheetViewModel() {

    val pagerSortType: MutableStateFlow<Map<Int, SortType>> = MutableStateFlow(
        mapOf(
            Pair(0, SortType.SAVE_RECENT),
            Pair(1, SortType.SAVE_RECENT)
        )
    )

    val myWantToWatchMovies: StateFlow<List<MyMovieItem>> = pagerSortType
        .flatMapLatest { sortType ->
            observeMyWantToWatchMovie()
                .map { movies ->
                    val item = movies.map(MyMovieItem::of)
                    sortMovie(sortType[1], item)
                }

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )


    val myRatedMovies: StateFlow<List<MyMovieItem>> = pagerSortType
        .flatMapLatest { sortType ->
            observeMyRatedMovie()
                .map { movies ->
                    val item = movies.map(MyMovieItem::of)
                    sortMovie(sortType[0], item)
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )

    private fun sortMovie(sortType: SortType?, movies: List<MyMovieItem>): List<MyMovieItem> {
        return when (sortType) {
            SortType.SAVE_RECENT -> { movies.sortedByDescending { it.createdAt } }
            SortType.SAVE_OLD -> { movies.sortedBy { it.createdAt } }
            SortType.MY_SCORE_HIGH -> { movies.sortedByDescending { it.rated } }
            SortType.MY_SCORE_LOW -> { movies.sortedBy { it.rated } }
            SortType.AVERAGE_SCORE_HIGH -> { movies.sortedByDescending { it.voteAverage } }
            SortType.AVERAGE_SCORE_LOW -> { movies.sortedBy { it.voteAverage } }
            else -> { movies.sortedByDescending { it.createdAt } }
        }
    }

    fun setSort(sortType: SortType, currentPage: Int) {
        val mutableSortType = pagerSortType.value.toMutableMap()
        mutableSortType[currentPage] = sortType
        pagerSortType.value = mutableSortType
    }

    override fun onLikeClick() {
        insertOrDeleteMyWantMovie()
    }

    override fun onTextChange(comment: String) {
        replaceRated(comment = comment)
    }

    override fun onRateChange(rate: Float) {
        replaceRated(rate = rate)
    }
}