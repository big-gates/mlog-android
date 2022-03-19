package com.kychan.mlog.domain.usecase.movie

import androidx.lifecycle.LiveData
import com.kychan.mlog.domain.repository.MovieRepository
import javax.inject.Inject

class GetItemTotal @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(): LiveData<Int> {
        return movieRepository.getItemTotal()
    }
}
