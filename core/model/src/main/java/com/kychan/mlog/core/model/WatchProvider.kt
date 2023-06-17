package com.kychan.mlog.core.model

data class WatchProvider(val id: Int, val rank: Int){

    companion object{
        const val NETFLIX_ID = 8
        const val WATCHA_ID = 97
        const val MLOG_ID = -1
    }
}
