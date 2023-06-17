package com.kychan.mlog.core.domain.observe

import com.kychan.mlog.core.data.repository.MyPageRepository
import com.kychan.mlog.core.model.MyMovie
import com.kychan.mlog.core.model.MyWantToWatchMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMyWantToWatchMovie @Inject constructor(
    private val myPageRepository: MyPageRepository
){

    operator fun invoke(): Flow<List<MyWantToWatchMovie>>{
        return myPageRepository.getMyWantToWatchMovies()
    }
}
