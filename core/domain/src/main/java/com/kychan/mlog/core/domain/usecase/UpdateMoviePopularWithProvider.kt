package com.kychan.mlog.core.domain.usecase

import com.kychan.mlog.core.common.network.Dispatcher
import com.kychan.mlog.core.common.network.Dispatchers
import com.kychan.mlog.core.data.repository.HomeRepository
import com.kychan.mlog.core.domain.UseCase
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchProvider
import com.kychan.mlog.core.model.WatchRegion
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class UpdateMoviePopularWithProvider @Inject constructor(
    private val homeRepository: HomeRepository,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): UseCase<UpdateMoviePopularWithProvider.Params, Unit>() {

    override suspend fun doWork(params: Params): Unit = withContext(ioDispatcher) {
        homeRepository.updateMoviePopularWithProvider(
            page = params.page,
            language = params.language,
            watchRegion =  params.watchRegion,
            withWatchProvider = params.withWatchProvider
        )
    }

    data class Params(
        val page: Int,
        val language: Language,
        val watchRegion: WatchRegion,
        val withWatchProvider: WatchProvider
    )
}
