package com.kychan.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kychan.core.common.result.Result
import com.kychan.core.domain.usecase.GetWatchaRanking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWatchaRanking: GetWatchaRanking
): ViewModel() {

    fun init() {
        viewModelScope.launch {
            val a = getWatchaRanking(GetWatchaRanking.Params(1)).collect{
                when(it){
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success -> {}
                }
            }
        }
    }

}