package com.kychan.mlog.feature.movie_detail.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.kychan.mlog.feature.movie_detail.MovieDetailScreen

const val movieDetailRoute = "movie_detail_route"

fun NavController.navigateToMovieDetail(id: Int, navOptions: NavOptions? = null) {
    this.navigate("$movieDetailRoute/$id", navOptions)
}

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
