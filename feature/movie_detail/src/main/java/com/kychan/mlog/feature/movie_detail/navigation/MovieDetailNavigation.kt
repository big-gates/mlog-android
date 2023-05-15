package com.kychan.mlog.feature.movie_detail.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.kychan.mlog.feature.movie_detail.MovieDetailScreen

const val movieDetailRoute = "movie_detail_route"

fun NavController.navigateToMovieDetail(navOptions: NavOptions? = null) {
    this.navigate(movieDetailRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.movieDetailScreen() {
    composable(route = movieDetailRoute) {
        MovieDetailScreen()
    }
}
