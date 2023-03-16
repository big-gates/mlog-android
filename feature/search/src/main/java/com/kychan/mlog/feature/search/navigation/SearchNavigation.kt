package com.kychan.mlog.feature.search.navigation

import androidx.compose.animation.*
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.kychan.mlog.feature.search.SearchRouter

const val searchRoute = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.searchScreen(){
    composable(
        route = searchRoute,
        enterTransition = { slideIn(
            initialOffset = { IntOffset(it.width,0) }
        ) },
        popEnterTransition = { slideIn(
            initialOffset = { IntOffset(it.width,0) }
        ) },
    ){
        SearchRouter()
    }
}

