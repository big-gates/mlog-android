package com.kychan.mlog.feature.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kychan.mlog.feature.mypage.MyPageRoute

const val myPageRoute = "myPage_route"

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) {
    this.navigate(myPageRoute, navOptions)
}

fun NavGraphBuilder.myPageScreen(
    navigateToMovieDetail: (id: Int) -> Unit,
) {
    composable(route = myPageRoute) {
        MyPageRoute(
            navigateToMovieDetail = navigateToMovieDetail,
        )
    }
}
