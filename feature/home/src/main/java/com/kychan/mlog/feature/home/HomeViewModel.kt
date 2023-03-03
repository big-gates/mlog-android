package com.kychan.mlog.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.common.extenstions.roundToTheFirstDecimal
import com.kychan.mlog.core.domain.observe.ObserveMoviePopular
import com.kychan.mlog.core.domain.usecase.UpdateMoviePopular
import com.kychan.mlog.core.domain.usecase.UpdateMoviePopularWithProvider
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchProvider
import com.kychan.mlog.core.model.WatchRegion
import com.kychan.mlog.feature.home.model.MovieCategory
import com.kychan.mlog.feature.home.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeMoviePopular: ObserveMoviePopular,
    private val updateMoviePopularWithProvider: UpdateMoviePopularWithProvider,
    private val updateMoviePopular: UpdateMoviePopular,
): ViewModel() {

    init {
        viewModelScope.launch {
            updateMoviePopular(
                UpdateMoviePopular.Params(
                    page = 1,
                    language = Language.KR,
                    watchRegion = WatchRegion.KR,
                )
            ).collect{

            }
        }
        viewModelScope.launch {
            updateMoviePopularWithProvider(
                UpdateMoviePopularWithProvider.Params(
                    page = 1,
                    language = Language.KR,
                    watchRegion = WatchRegion.KR,
                    withWatchProvider = WatchProvider.Netflix
                )
            ).collect{

            }
        }
        viewModelScope.launch {
            updateMoviePopularWithProvider(
                UpdateMoviePopularWithProvider.Params(
                    page = 1,
                    language = Language.KR,
                    watchRegion = WatchRegion.KR,
                    withWatchProvider = WatchProvider.Watcha
                )
            ).collect{

            }
        }
    }

    val movieRankingsByCategory = combine(
        observeMoviePopular(ObserveMoviePopular.Params(WatchProvider.None)),
        observeMoviePopular(ObserveMoviePopular.Params(WatchProvider.Netflix)),
        observeMoviePopular(ObserveMoviePopular.Params(WatchProvider.Watcha)),
    ){ popular, netflix, watcha ->
        listOf(
            MovieCategory(
                title = MLOG_RECOMMENDATION,
                movieItems = popular.mapIndexed { index, movie ->
                    MovieItem(
                        image = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}$POSTER_SIZE/${movie.posterPath}",
                        rank = "${index+1}",
                        rating = movie.voteAverage.roundToTheFirstDecimal().toFloat(),
                        title = movie.title,
                    )
                },
                watchProvider = WatchProvider.None
            ),
            MovieCategory(
                title = NETFLIX_RECOMMENDATION,
                movieItems = netflix.mapIndexed { index, movie ->
                    MovieItem(
                        image = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}$POSTER_SIZE/${movie.posterPath}",
                        rank = "${index+1}",
                        rating = movie.voteAverage.roundToTheFirstDecimal().toFloat(),
                        title = movie.title,
                    )
                },
                watchProvider = WatchProvider.Netflix
            ),
            MovieCategory(
                title = WATCHA_RECOMMENDATION,
                movieItems = watcha.mapIndexed { index, movie ->
                    MovieItem(
                        image = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}$POSTER_SIZE/${movie.posterPath}",
                        rank = "${index+1}",
                        rating = movie.voteAverage.roundToTheFirstDecimal().toFloat(),
                        title = movie.title,
                    )
                },
                watchProvider = WatchProvider.Watcha
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = listOf()
    )

    companion object{
        private const val POSTER_SIZE = "w154"
        private const val MLOG_RECOMMENDATION = "Mlog 추천 Pick"
        private const val NETFLIX_RECOMMENDATION = "Mlog가 추천하는 Netflix"
        private const val WATCHA_RECOMMENDATION = "Mlog가 추천하는 Watcha"
    }
}