package com.kychan.mlog.feature.home.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.*

import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.kychan.mlog.feature.home.HomeRoute
import com.kychan.mlog.feature.home.detail.HomeDetailRoute

const val homeGraph = "home_graph"
const val homeRoute = "home_route"
const val homeDetailRoute = "home_detail_route"
const val watchProvidersIdArg = "watchProvidersId"

internal class HomeDetailArgs(val watchProviderId: Int) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle.get(watchProvidersIdArg) as? Int))
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavController.navigateToHomeDetail(watchProviderId: Int){
    this.navigate("$homeDetailRoute/${watchProviderId}")
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeGraph(
    navigateToHomeDetail: (watchProviderId: Int) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToMovieDetail: (id: Int) -> Unit,
) {
    navigation(
        route = homeGraph,
        startDestination = homeRoute
    ){
        composable(route = homeRoute) {
            HomeRoute(
                navigateToHomeDetail =  navigateToHomeDetail,
                navigateToSearch = navigateToSearch,
                navigateToMovieDetail = navigateToMovieDetail,
            )
        }

        composable(
            route = "$homeDetailRoute/{$watchProvidersIdArg}",
            arguments = listOf(
                navArgument(watchProvidersIdArg) { type = NavType.IntType }
            ),
            enterTransition = { scaleIn() },
            exitTransition = { scaleOut() },
            popEnterTransition = { scaleIn() },
            popExitTransition = { scaleOut() }
        ) {
            HomeDetailRoute(navigateToMovieDetail = navigateToMovieDetail)
        }
    }
}
