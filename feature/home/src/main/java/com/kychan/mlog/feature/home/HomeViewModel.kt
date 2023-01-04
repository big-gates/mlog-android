package com.kychan.mlog.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.mlog.core.common.extenstions.roundToTheFirstDecimal
import com.kychan.mlog.core.domain.usecase.GetMoviePopular
import com.kychan.mlog.core.domain.usecase.GetMoviePopularWithProvider
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchProviders
import com.kychan.mlog.core.model.WatchRegion
import com.kychan.mlog.feature.home.model.MovieCategory
import com.kychan.mlog.feature.home.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviePopularWithProvider: GetMoviePopularWithProvider,
    private val getMoviePopular: GetMoviePopular,
): ViewModel() {

    val movieRankingsByCategory = combine(
        getMoviePopular(
          GetMoviePopular.Params(
              page = 1,
              language = Language.KR,
              watchRegion = WatchRegion.KR,
          )
        ),
        getMoviePopularWithProvider(
            GetMoviePopularWithProvider.Params(
                page = 1,
                language = Language.KR,
                watchRegion = WatchRegion.KR,
                withWatchProviders = WatchProviders.Netflix
            )
        ),
        getMoviePopularWithProvider(
            GetMoviePopularWithProvider.Params(
                page = 1,
                language = Language.KR,
                watchRegion = WatchRegion.KR,
                withWatchProviders = WatchProviders.Watcha
            )
        )
    ){ popular, netflix, watcha ->
        listOf(
            MovieCategory(
                title = MLOG_RECOMMENDATION,
                movieItems = popular.mapIndexed { index, movie ->
                    MovieItem(
                        image = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}w154/${movie.posterPath}",
                        rank = "${index+1}",
                        rating = movie.voteAverage.roundToTheFirstDecimal().toFloat(),
                        title = movie.title,
                    )
                }
            ),
            MovieCategory(
                title = NETFLIX_RECOMMENDATION,
                movieItems = netflix.mapIndexed { index, movie ->
                    MovieItem(
                        image = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}w154/${movie.posterPath}",
                        rank = "${index+1}",
                        rating = movie.voteAverage.roundToTheFirstDecimal().toFloat(),
                        title = movie.title,
                    )
                }
            ),
            MovieCategory(
                title = WATCHA_RECOMMENDATION,
                movieItems = watcha.mapIndexed { index, movie ->
                    MovieItem(
                        image = "${BuildConfig.THE_MOVIE_DB_IMAGE_URL}w154/${movie.posterPath}",
                        rank = "${index+1}",
                        rating = movie.voteAverage.roundToTheFirstDecimal().toFloat(),
                        title = movie.title,
                    )
                }
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = listOf()
    )

    companion object{
        private const val MLOG_RECOMMENDATION = "Mlog 추천 Pick"
        private const val NETFLIX_RECOMMENDATION = "Mlog가 추천하는 Netflix"
        private const val WATCHA_RECOMMENDATION = "Mlog가 추천하는 Watcha"
    }
}