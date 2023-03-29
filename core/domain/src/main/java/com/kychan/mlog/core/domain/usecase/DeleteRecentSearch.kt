package com.kychan.mlog.core.domain.usecase

import com.kychan.mlog.core.data.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteRecentSearch @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend operator fun invoke(id: Int) = withContext(Dispatchers.IO){
        searchRepository.deleteRecentSearch(id)
    }
}