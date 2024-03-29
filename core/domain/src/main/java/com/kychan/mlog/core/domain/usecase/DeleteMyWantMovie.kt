package com.kychan.mlog.core.domain.usecase

import com.kychan.mlog.core.data.repository.MyPageRepository
import com.kychan.mlog.core.model.MyMovie
import com.kychan.mlog.core.model.WantToWatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteMyWantMovie @Inject constructor(
    private val myPageRepository: MyPageRepository
) {

    suspend operator fun invoke(myMovie: MyMovie, wantToWatch: WantToWatch) = withContext(Dispatchers.IO) {
        myPageRepository.deleteMyWantMovie(myMovie, wantToWatch)
    }
}