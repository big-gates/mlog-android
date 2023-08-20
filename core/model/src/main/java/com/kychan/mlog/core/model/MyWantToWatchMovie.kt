package com.kychan.mlog.core.model

data class MyWantToWatchMovie(
    val myMovie: MyMovie,
    val wantToWatch: WantToWatch,
    val genres: List<Genre>
)
