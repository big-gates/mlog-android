package com.kychan.mlog.core.domain.usecase

import com.kychan.mlog.core.common.network.Dispatcher
import com.kychan.mlog.core.common.network.Dispatchers.IO
import com.kychan.mlog.core.domain.UseCase
import com.kychan.mlog.core.model.TvSeriesEntity
import com.kychan.mlog.core.data.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWatchaRanking @Inject constructor(
    private val homeRepository: HomeRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): UseCase<GetWatchaRanking.Params, List<TvSeriesEntity>>() {

    override suspend fun doWork(params: Params): List<TvSeriesEntity> = withContext(ioDispatcher){
        homeRepository.getWatchaRanking(params.page, params.language)
    }

    data class Params(val page: Int, val language: String = "kr")
}
