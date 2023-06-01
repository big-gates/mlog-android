package com.kychan.mlog.feature.mypage.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.kychan.mlog.feature.mypage.MyPageRoute

const val myPageRoute = "myPage_route"

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) {
    this.navigate(myPageRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.myPageScreen(
    navigateToMovieDetail: (id: Int) -> Unit,
) {
    composable(route = myPageRoute) {
        MyPageRoute(
            navigateToMovieDetail = navigateToMovieDetail,
        )
    }
}
