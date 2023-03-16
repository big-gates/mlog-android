package com.kychan.mlog.feature.search

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchRouter(){
    SearchScreen()
}

@Composable
fun SearchScreen(){
    Text(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(),
        text = "Search Screen"
    )
}