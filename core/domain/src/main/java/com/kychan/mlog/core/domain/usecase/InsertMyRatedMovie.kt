package com.kychan.mlog.core.domain.usecase

import com.kychan.mlog.core.data.repository.MyPageRepository
import com.kychan.mlog.core.model.MyMovie
import com.kychan.mlog.core.model.Rated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertMyRatedMovie @Inject constructor(
    private val myPageRepository: MyPageRepository
) {

    suspend operator fun invoke(myMovie: MyMovie, rated: Rated) = withContext(Dispatchers.IO) {
        myPageRepository.insertMyRatedMovie(myMovie, rated)
    }
}