package com.kychan.mlog.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.kychan.mlog.feature.home.navigation.homeGraph
import com.kychan.mlog.feature.home.navigation.navigateToHomeDetail
import com.kychan.mlog.feature.movie_detail.navigation.movieDetailScreen
import com.kychan.mlog.feature.movie_detail.navigation.navigateToMovieDetail
import com.kychan.mlog.feature.mypage.navigation.myPageScreen
import com.kychan.mlog.feature.search.navigation.navigateToSearch
import com.kychan.mlog.feature.search.navigation.searchScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MLogNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = homeGraph
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        homeGraph(
            navigateToHomeDetail = { watchProviderId ->
                navController.navigateToHomeDetail(watchProviderId)
            },
            navigateToSearch = { navController.navigateToSearch() },
            navigateToMovieDetail = { id ->
                navController.navigateToMovieDetail(id)
            }
        )
        myPageScreen(
            navigateToMovieDetail = { id ->
                navController.navigateToMovieDetail(id)
            }
        )
        searchScreen(
            onBackClick = navController::popBackStack,
            navigateToMovieDetail = { id ->
                navController.navigateToMovieDetail(id)
            }
        )
        movieDetailScreen()
    }
}
