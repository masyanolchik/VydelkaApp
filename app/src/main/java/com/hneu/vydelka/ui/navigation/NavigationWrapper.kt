package com.hneu.vydelka.ui.navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hneu.vydelka.ui.categories.Categories
import com.hneu.vydelka.ui.favorites.Favorites
import com.hneu.vydelka.ui.feed.Feed

@Composable
fun NavigationWrapper(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
) {
    NavHost(navController = navController, startDestination = BottomMenuItem.FeedScreen.route, modifier = Modifier.padding(paddingValues)) {
        bottomNavigation()
    }
}

@Composable
fun DummyScreen() {
    Text(
        text = "DUMMY"
    )
}

fun NavGraphBuilder.bottomNavigation() {
    composable(BottomMenuItem.FeedScreen.route) {
        Feed()
    }
    composable(BottomMenuItem.CatalogueScreen.route) {
        Categories()
    }
    composable(BottomMenuItem.FavoritesScreen.route) {
        Favorites()
    }
    composable(BottomMenuItem.ProfileScreen.route) {
        DummyScreen()
    }
}