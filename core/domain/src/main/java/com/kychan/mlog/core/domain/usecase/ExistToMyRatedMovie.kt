package com.kychan.mlog.core.domain.usecase

import com.kychan.mlog.core.data.repository.MyPageRepository
import com.kychan.mlog.core.model.Rated
import javax.inject.Inject

class ExistToMyRatedMovie @Inject constructor(
    private val myPageRepository: MyPageRepository
){

    suspend operator fun invoke(id: Int): Rated? {
        return myPageRepository.existToMyRatedMovie(id)
    }
}
