package com.kychan.mlog.feature.search

import androidx.lifecycle.ViewModel
import com.kychan.mlog.feature.search.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

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

    private val _recentSearchList: MutableStateFlow<List<String>> = MutableStateFlow(listOf(
        "한니발",
        "하나와 엘리스",
        "하얀 리본",
        "하울의 움직이는 성",
        "하이 랜더",
        "한산",
        "a",
        "b",
        "c",
        "d",
        "e",
        "f",
        "g",
        "h",
        "i",
        "j",
        "k",
        "곽하민",
        "abcd",
        "유럽",
        "프랑스",
        "런던"
    ))
    val recentSearchList: StateFlow<List<String>>
        get() = _recentSearchList

    val movies: StateFlow<List<MovieItem>> = MutableStateFlow(
        (0 until 50).map {
            MovieItem(id = it, images[it])
        }
    )

    fun updateSearchText(text: String){
        _searchText.value = text
    }

}