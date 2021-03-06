package com.kychan.mlog.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kychan.mlog.data.factory.SearchMovieDataSourceFactory
import com.kychan.mlog.data.local.MovieLocalDataSource
import com.kychan.mlog.data.local.model.MovieEntity
import com.kychan.mlog.domain.repository.MovieRepository
import com.kychan.mlog.presentation.main.mypage.MyMovieItem
import com.kychan.mlog.presentation.main.search.SearchMovieItem
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val searchMovieDataSourceFactory: SearchMovieDataSourceFactory
) : MovieRepository {
    override fun invalidateDataSource() {
        searchMovieDataSourceFactory.liveData.value?.invalidate()
    }

    override fun setSearchKeyword(keyword: String) {
        searchMovieDataSourceFactory.setSearchKeyword(keyword)
    }

    override fun getItemTotal(): LiveData<Int> {
        return Transformations.switchMap(
            searchMovieDataSourceFactory.liveData, SearchMovieDataSource::itemTotal
        )
    }

    override fun getSearchMovieList(): LiveData<PagedList<SearchMovieItem>> {
        return LivePagedListBuilder(
            searchMovieDataSourceFactory,
            SearchMovieDataSourceFactory.pagedListConfig()
        ).build()
    }

    override fun getMovieAll(): LiveData<PagedList<MyMovieItem>> {
        return LivePagedListBuilder(
            movieLocalDataSource.getMovieAll().map {
                it.toMovieItem()
            },
            SearchMovieDataSourceFactory.pagedListConfig()
        ).build()
    }

    override fun insertMovie(movieEntity: MovieEntity) {
        movieLocalDataSource.insertMovie(movieEntity)
    }

    override fun deleteMovie(link: String) {
        movieLocalDataSource.deleteMovie(link)
    }

    override fun updateMovie(evaluation: Float, link: String) {
        movieLocalDataSource.updateMovie(evaluation, link)
    }
}
