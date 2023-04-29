package com.kychan.mlog.core.domain.observe

import androidx.paging.PagingData
import com.kychan.mlog.core.data.repository.SearchRepository
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.WatchRegion
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMovieSearch @Inject constructor(
    private val searchRepository: SearchRepository
){

    operator fun invoke(
        query: String,
        language: Language,
        watchRegion: WatchRegion,
    ): Flow<PagingData<Movie>>{
        return searchRepository.getSearch(
            query = query,
            language = language,
            watchRegion = watchRegion
        )
    }
}
