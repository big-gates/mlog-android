package com.kychan.mlog.presentation.main

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kychan.mlog.navigation.BottomNavItem
import com.kychan.mlog.navigation.MLogNavHost

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun MLogApp(){
    val navController = rememberAnimatedNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        val context = LocalContext.current
        Box(Modifier.padding(it)) {
            MLogNavHost(
                navController = navController,
                onBackClick = {
                    Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.MyPage
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(backgroundColor = MaterialTheme.colors.background) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = { Text(text = item.title, fontSize = 12.sp) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Gray,
                selected = currentDestination?.hierarchy?.any {
                    it.route == item.screenRoute
                } == true,
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

    }
}