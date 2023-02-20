package com.kychan.mlog.core.domain.observe

import androidx.paging.PagingData
import com.kychan.mlog.core.data.repository.HomeRepository
import com.kychan.mlog.core.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMlogMovie @Inject constructor(
    private val homeRepository: HomeRepository
){

    operator fun invoke(): Flow<PagingData<Movie>>{
        return homeRepository.getMlogMovie()
    }
}
