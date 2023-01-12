package com.kychan.mlog.core.domain.observe

import com.kychan.mlog.core.data.repository.HomeRepository
import com.kychan.mlog.core.domain.ObserveUseCase
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.WatchProviders
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMoviePopular @Inject constructor(
    private val homeRepository: HomeRepository
): ObserveUseCase<ObserveMoviePopular.Params, List<Movie>>(){

    override fun createObservable(params: Params): Flow<List<Movie>> {
        return homeRepository.getPopularMoviesWithCategory(params.watchProviders)
    }

    data class Params(val watchProviders: WatchProviders)
}
