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
import com.hneu.vydelka.ui.categories.category.CategoryScreen
import com.hneu.vydelka.ui.favorites.Favorites
import com.hneu.vydelka.ui.feed.Feed
import com.hneu.vydelka.ui.product.ProductScreen
import com.hneu.vydelka.ui.profile.Profile

@Composable
fun NavigationWrapper(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    onNavigate: (String) -> Unit,
) {
    NavHost(navController = navController, startDestination = BottomMenuItem.FeedScreen.route, modifier = Modifier.padding(paddingValues)) {
        navigateTo(navController, onNavigate)
    }
}

fun NavGraphBuilder.navigateTo(navController: NavHostController, onNavigate: (String) -> Unit) {
    composable(BottomMenuItem.FeedScreen.route) {
        onNavigate(BottomMenuItem.FeedScreen.route)
        Feed(navController)
    }
    composable(BottomMenuItem.CatalogueScreen.route) {
        onNavigate(BottomMenuItem.CatalogueScreen.route)
        Categories(navController)
    }
    composable(BottomMenuItem.FavoritesScreen.route) {
        onNavigate(BottomMenuItem.FavoritesScreen.route)
        Favorites(navController)
    }
    composable(BottomMenuItem.ProfileScreen.route) {
        onNavigate(BottomMenuItem.ProfileScreen.route)
        Profile(navController)
    }
    composable("${NavigationRoutes.CategoryRoute.route}{${NavigationRoutes.CategoryRoute.idRoute}}") { backStackEntry ->
        val id = backStackEntry.arguments?.getInt(NavigationRoutes.CategoryRoute.idRoute) ?: -1
        onNavigate(NavigationRoutes.CategoryRoute.route)
        CategoryScreen(navController = navController, id = id)
    }
    composable("${NavigationRoutes.ProductRoute.route}{${NavigationRoutes.ProductRoute.idRoute}}") { backStackEntry ->
        val id = backStackEntry.arguments?.getInt(NavigationRoutes.ProductRoute.idRoute) ?: -1
        onNavigate(NavigationRoutes.ProductRoute.route)
        ProductScreen(navController = navController, id = id)
    }
}