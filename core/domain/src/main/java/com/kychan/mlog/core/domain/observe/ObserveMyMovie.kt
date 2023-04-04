package com.kychan.mlog.core.domain.observe

import com.kychan.mlog.core.data.repository.MyPageRepository
import com.kychan.mlog.core.model.MyRatedMovies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMyRatedMovie @Inject constructor(
    private val myPageRepository: MyPageRepository
){

    operator fun invoke(): Flow<List<MyRatedMovies>>{
        return myPageRepository.getMyRatedMovies()
    }
}
