package com.kychan.mlog.feature.movie_detail.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.kychan.mlog.feature.movie_detail.MovieDetailScreen

const val movieDetailRoute = "movie_detail_route"

fun NavController.navigateToMovieDetail(id: Int, navOptions: NavOptions? = null) {
    this.navigate("$movieDetailRoute/$id", navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.movieDetailScreen() {
    composable(
        route = "$movieDetailRoute/{movieId}",
        arguments = listOf(navArgument("movieId") {
            type = NavType.IntType
            defaultValue = -1
            nullable = false
        }),
    ) {
        MovieDetailScreen()
    }
}
