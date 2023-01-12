package com.kychan.mlog.feature.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val myPageRoute = "myPage_route"

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) {
    this.navigate(myPageRoute, navOptions)
}

fun NavGraphBuilder.myPageScreen() {
    composable(route = myPageRoute) {
        MyPageScreen()
    }
}
