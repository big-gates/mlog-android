package com.kychan.mlog.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.kychan.mlog.feature.home.navigation.homeGraph
import com.kychan.mlog.feature.home.navigation.navigateToHomeDetail
import com.kychan.mlog.feature.mypage.navigation.myPageScreen

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
            navigateToHomeDetail = { watchProviders ->
                navController.navigateToHomeDetail(watchProviders)
            }
        )
        myPageScreen()
    }
}
