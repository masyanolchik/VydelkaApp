package com.hneu.vydelka.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hneu.vydelka.ui.navigation.BottomMenu
import com.hneu.vydelka.ui.navigation.NavigationWrapper
import com.hneu.vydelka.ui.navigation.TopBar

@Composable
fun VydelkaApp() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController = navController, scrollState = scrollState)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomMenu(navController = navController)
        },
    ) {
        NavigationWrapper(navController, scrollState, it)
    }
}
