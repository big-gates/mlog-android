package com.kychan.mlog.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kychan.mlog.feature.home.navigation.homeRoute
import com.kychan.mlog.feature.home.navigation.homeScreen
import com.kychan.mlog.feature.mypage.navigation.myPageScreen

@Composable
fun MLogNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen()
        myPageScreen()
    }
}
