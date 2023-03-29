package com.kychan.mlog.core.domain.observe

import com.kychan.mlog.core.data.repository.SearchRepository
import com.kychan.mlog.core.model.RecentSearch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRecentSearch @Inject constructor(
    private val searchRepository: SearchRepository
){

    operator fun invoke(): Flow<List<RecentSearch>>{
        return searchRepository.getRecentSearch()
    }
}
