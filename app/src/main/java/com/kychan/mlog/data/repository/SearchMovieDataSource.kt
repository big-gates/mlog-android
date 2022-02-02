package com.kychan.mlog.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.kychan.mlog.data.remote.NaverApi
import com.kychan.mlog.data.remote.model.SearchMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchMovieDataSource @Inject constructor(
    private val naverApi: NaverApi
) : PositionalDataSource<SearchMovieResponse.MovieResponse>() {

    val itemTotal = MutableLiveData<Int>()

    private var searchKeyword: String? = null

    fun setSearchKeyword(keyword: String) {
        searchKeyword = keyword
    }

    private fun fetchData(startPosition: Int, loadCount: Int, callback: (SearchMovieResponse) -> Unit) {
        naverApi.getSearchMovie(query = searchKeyword.orEmpty(), start = startPosition, display = loadCount)
            .enqueue(object : Callback<SearchMovieResponse> {
                override fun onResponse(call: Call<SearchMovieResponse>, response: Response<SearchMovieResponse>) {
                    Log.d("TAG", "성공 : ${response.raw()}")
                    itemTotal.postValue(response.body()?.total)

                    callback(response.body() ?: return)
                }

                override fun onFailure(call: Call<SearchMovieResponse>, t: Throwable) {
                    Log.d("TAG", "실패 : $t")
                }
            })
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<SearchMovieResponse.MovieResponse>) {
        fetchData(1, params.requestedLoadSize) { searchMovieResponse ->
            val totalCount = searchMovieResponse.total
            val position = computeInitialLoadPosition(params, totalCount)
            callback.onResult(searchMovieResponse.movieResponseList, position, totalCount)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<SearchMovieResponse.MovieResponse>) {
        fetchData(params.startPosition + 1, params.loadSize) {
            callback.onResult(it.movieResponseList)
        }
    }
}
