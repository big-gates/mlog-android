package com.kychan.mlog.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kychan.mlog.feature.search.SearchRouter

const val searchRoute = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchRoute, navOptions)
}

fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit,
    navigateToMovieDetail: (id: Int) -> Unit,
) {
    composable(
        route = searchRoute,
    ){
        SearchRouter(
            onBackClick = onBackClick,
            navigateToMovieDetail = navigateToMovieDetail,
        )
    }
}

