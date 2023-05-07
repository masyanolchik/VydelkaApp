package com.hneu.vydelka.ui.navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hneu.vydelka.ui.categories.Categories
import com.hneu.vydelka.ui.favorites.Favorites
import com.hneu.vydelka.ui.feed.Feed
import com.hneu.vydelka.ui.profile.Profile

@Composable
fun NavigationWrapper(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    onNavigate: (String) -> Unit,
) {
    NavHost(navController = navController, startDestination = BottomMenuItem.FeedScreen.route, modifier = Modifier.padding(paddingValues)) {
        bottomNavigation(onNavigate)
    }
}

fun NavGraphBuilder.bottomNavigation(onNavigate: (String) -> Unit) {
    composable(BottomMenuItem.FeedScreen.route) {
        Feed()
        onNavigate(BottomMenuItem.FeedScreen.route)
    }
    composable(BottomMenuItem.CatalogueScreen.route) {
        Categories()
        onNavigate(BottomMenuItem.CatalogueScreen.route)
    }
    composable(BottomMenuItem.FavoritesScreen.route) {
        Favorites()
        onNavigate(BottomMenuItem.FavoritesScreen.route)
    }
    composable(BottomMenuItem.ProfileScreen.route) {
        Profile()
        onNavigate(BottomMenuItem.ProfileScreen.route)
    }
}