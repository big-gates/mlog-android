package com.kychan.mlog.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.domain.observe.ObserveRecentSearch
import com.kychan.mlog.core.domain.usecase.DeleteAllRecentSearch
import com.kychan.mlog.core.domain.usecase.DeleteRecentSearch
import com.kychan.mlog.core.domain.usecase.UpdateRecentSearch
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
):ViewModel() {

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    val searchText: StateFlow<String>
        get() = _searchText

    private val images = listOf(
        "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/1/b0/5269678709fb7.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/9/30/535feab462a64.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/3/80/4c00358ec7548.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/a/f0/5202887448860.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/5/e0/4c0035c9c425d.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/c/a0/4ce5a9bf70e19.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/4/60/52695285d6e7e.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/f/60/4c0042121d790.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/9/a0/4ce18a834b7f5.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/2/80/4c002f35c5215.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/70/4c0035adc7d3a.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce5a385a2e82.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/2/c0/4c00377144d5a.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/6/70/4cd061e6d6573.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/4c003d40ac7ae.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/1/60/52695277ee088.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/3/80/520288b9cb581.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/f/80/4ce5a6d8b8f2a.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/b0/4ce59ea2103ac.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/d/03/531769834b15f.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/4/50/531769ae4399f.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
        "https://i.annihil.us/u/prod/marvel/i/mg/7/00/545a82f59dd73.jpg",
    )

    val recentSearchList: StateFlow<List<RecentSearchView>> = observeRecentSearch()
        .map { recentSearches ->
            recentSearches.map { it.toView() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = listOf()
        )

    val movies: StateFlow<List<MovieItem>> = MutableStateFlow(
        (0 until 50).map {
            MovieItem(id = it, images[it])
        }
    )

    fun updateSearchText(text: String){
        _searchText.value = text
    }

    fun search() = viewModelScope.launch {
        updateRecentSearch(searchText.value)
    }
}