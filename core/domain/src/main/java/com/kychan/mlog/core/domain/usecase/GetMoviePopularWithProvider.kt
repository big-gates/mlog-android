package com.kychan.mlog.core.domain.usecase

import com.kychan.mlog.core.common.network.Dispatcher
import com.kychan.mlog.core.common.network.Dispatchers
import com.kychan.mlog.core.data.repository.HomeRepository
import com.kychan.mlog.core.domain.UseCase
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.WatchProviders
import com.kychan.mlog.core.model.WatchRegion
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class GetMoviePopularWithProvider @Inject constructor(
    private val homeRepository: HomeRepository,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): UseCase<GetMoviePopularWithProvider.Params, List<Movie>>() {

    override suspend fun doWork(params: Params): List<Movie> = withContext(ioDispatcher) {
        homeRepository.getMoviePopularWithProvider(
            page = params.page,
            language = params.language,
            watchRegion =  params.watchRegion,
            withWatchProviders = params.withWatchProviders
        )
    }

    data class Params(
        val page: Int,
        val language: Language,
        val watchRegion: WatchRegion,
        val withWatchProviders: WatchProviders
    )
}
