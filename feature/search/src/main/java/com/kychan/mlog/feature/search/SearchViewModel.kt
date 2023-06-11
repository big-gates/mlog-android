package com.kychan.mlog.feature.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kychan.mlog.core.domain.observe.ObserveMovieSearch
import com.kychan.mlog.core.domain.observe.ObserveRecentSearch
import com.kychan.mlog.core.domain.usecase.DeleteAllRecentSearch
import com.kychan.mlog.core.domain.usecase.DeleteRecentSearch
import com.kychan.mlog.core.domain.usecase.UpdateRecentSearch
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion
import com.kychan.mlog.feature.movie_modal.MovieModalBottomSheetViewModel
import com.kychan.mlog.feature.search.model.MovieItem
import com.kychan.mlog.feature.search.model.RecentSearchView
import com.kychan.mlog.feature.search.model.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val observeRecentSearch: ObserveRecentSearch,
    private val updateRecentSearch: UpdateRecentSearch,
    private val deleteAllRecentSearch: DeleteAllRecentSearch,
    private val deleteRecentSearch: DeleteRecentSearch,
    private val observeMovieSearch: ObserveMovieSearch,
) : MovieModalBottomSheetViewModel() {

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    val searchText: StateFlow<String>
        get() = _searchText

    val recentSearchList: StateFlow<List<RecentSearchView>> = observeRecentSearch()
        .map { recentSearches ->
            recentSearches.map { it.toView() }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = listOf()
        )

    val movies: StateFlow<PagingData<MovieItem>> = _searchText
        .filter { it.isNotEmpty() }
        .flatMapLatest {
            observeMovieSearch(
                query = it,
                language = Language.KR,
                watchRegion = WatchRegion.KR
            ).map { paging ->
                paging.map { movie -> movie.toView(POSTER_SIZE) }
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = PagingData.empty()
        )

    fun updateSearchText(text: String){
        _searchText.value = text
    }

    fun search() = viewModelScope.launch {
        updateRecentSearch(searchText.value)
    }

    fun deleteAll() = viewModelScope.launch {
        deleteAllRecentSearch()
    }

    fun delete(id:Int) = viewModelScope.launch {
        deleteRecentSearch(id)
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

    companion object{
        const val POSTER_SIZE = "w154"
    }
}