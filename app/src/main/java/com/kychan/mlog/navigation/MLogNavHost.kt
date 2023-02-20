package com.kychan.mlog.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.kychan.mlog.feature.home.navigation.homeRoute
import com.kychan.mlog.feature.home.navigation.homeScreen
import com.kychan.mlog.feature.mypage.navigation.myPageScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MLogNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        homeScreen()
        myPageScreen()
    }
}
