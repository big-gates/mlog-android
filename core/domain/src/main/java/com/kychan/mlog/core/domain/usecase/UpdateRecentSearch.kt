package com.kychan.mlog.core.domain.usecase

import com.kychan.mlog.core.data.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateRecentSearch @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend operator fun invoke(text: String) = withContext(Dispatchers.IO){
        searchRepository.updateRecentSearch(text)
    }
}