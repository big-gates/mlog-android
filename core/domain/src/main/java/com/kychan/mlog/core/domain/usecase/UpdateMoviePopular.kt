package com.kychan.mlog.core.domain.usecase

import com.kychan.mlog.core.common.network.Dispatcher
import com.kychan.mlog.core.common.network.Dispatchers.IO
import com.kychan.mlog.core.domain.UseCase
import com.kychan.mlog.core.data.repository.HomeRepository
import com.kychan.mlog.core.model.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateMoviePopular @Inject constructor(
    private val homeRepository: HomeRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): UseCase<UpdateMoviePopular.Params, Unit>() {

    override suspend fun doWork(params: Params): Unit = withContext(ioDispatcher){
        homeRepository.updateMoviePopular(params.page, params.language, params.watchRegion)
    }

    data class Params(
        val page: Int,
        val language: Language,
        val watchRegion: WatchRegion,
    )
}
