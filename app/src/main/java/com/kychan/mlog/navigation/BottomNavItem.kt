package com.kychan.mlog.navigation

import com.kychan.mlog.R
import com.kychan.mlog.feature.home.navigation.homeRoute
import com.kychan.mlog.feature.mypage.navigation.myPageRoute

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val screenRoute: String
) {
    object Home : BottomNavItem("홈", R.drawable.ic_home, homeRoute)
    object MyPage : BottomNavItem("보관함", R.drawable.ic_person, myPageRoute)
}
