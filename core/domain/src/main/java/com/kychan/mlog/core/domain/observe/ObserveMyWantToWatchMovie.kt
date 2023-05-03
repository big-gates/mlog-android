package com.kychan.mlog.core.domain.observe

import com.kychan.mlog.core.data.repository.MyPageRepository
import com.kychan.mlog.core.model.MyMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMyWantToWatchMovie @Inject constructor(
    private val myPageRepository: MyPageRepository
){

    operator fun invoke(): Flow<List<MyMovie>>{
        return myPageRepository.getMyWantToWatchMovies()
    }
}
