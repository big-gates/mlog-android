package com.kychan.mlog.core.domain.usecase

import com.kychan.mlog.core.data.repository.MyPageRepository
import javax.inject.Inject

class ExistToMyWantMovie @Inject constructor(
    private val myPageRepository: MyPageRepository
){

    suspend operator fun invoke(id: Int): Int {
        return myPageRepository.existToMyWantMovie(id)
    }
}
