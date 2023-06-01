package com.kychan.mlog.core.domain.observe

import com.kychan.mlog.core.data.repository.MovieDetailRepository
import com.kychan.mlog.core.model.MyMovieRatedAndWanted
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMyMovieRatedAndWanted @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {

    operator fun invoke(id: Int): Flow<MyMovieRatedAndWanted> {
        return movieDetailRepository.getMyMovieRatedAndWanted(id)
    }
}
