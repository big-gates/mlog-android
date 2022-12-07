package com.kychan.core.domain.usecase

import com.kychan.core.common.network.Dispatcher
import com.kychan.core.common.network.Dispatchers.IO
import com.kychan.core.domain.UseCase
import com.kychan.core.entity.TvSeriesEntity
import com.kychan.core.data.repository.HomeRepository
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
