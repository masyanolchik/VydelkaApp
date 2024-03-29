package com.hneu.vydelka.ui.navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hneu.core.domain.order.Cart
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.product.Tag
import com.hneu.vydelka.ui.categories.Categories
import com.hneu.vydelka.ui.categories.category.CategoryScreen
import com.hneu.vydelka.ui.favorites.Favorites
import com.hneu.vydelka.ui.feed.Feed
import com.hneu.vydelka.ui.product.ProductScreen
import com.hneu.vydelka.ui.profile.Profile
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun NavigationWrapper(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    cartState: Cart,
    favoritesState: Result<List<Product>>,
    scrollState: ScrollState,
    searchStateFlow: StateFlow<String>,
    tagsMutableStateFlow: MutableStateFlow<List<Tag>>,
    paddingValues: PaddingValues,
    onNavigate: (String) -> Unit,
) {
    NavHost(navController = navController, startDestination = BottomMenuItem.FeedScreen.route, modifier = Modifier.padding(paddingValues)) {
        navigateTo(cartState,snackbarHostState, favoritesState, navController, onNavigate, searchStateFlow, tagsMutableStateFlow)
    }
}

fun NavGraphBuilder.navigateTo(
    cartState: Cart,
    snackbarHostState: SnackbarHostState,
    favoritesState: Result<List<Product>>,
    navController: NavHostController,
    onNavigate: (String) -> Unit,
    searchStateFlow: StateFlow<String>,
    tagsMutableStateFlow: MutableStateFlow<List<Tag>>,
    ) {
    composable(BottomMenuItem.FeedScreen.route) {
        onNavigate(BottomMenuItem.FeedScreen.route)
        Feed(tagsMutableStateFlow, cartState, navController,favoritesState)
    }
    composable(BottomMenuItem.CatalogueScreen.route) {
        onNavigate(BottomMenuItem.CatalogueScreen.route)
        Categories(navController = navController, searchStateFlow = searchStateFlow, searchTags = tagsMutableStateFlow)
    }
    composable(BottomMenuItem.FavoritesScreen.route) {
        onNavigate(BottomMenuItem.FavoritesScreen.route)
        Favorites(cartState, navController)
    }
    composable(BottomMenuItem.ProfileScreen.route) {
        onNavigate(BottomMenuItem.ProfileScreen.route)
        Profile(navController = navController, snackbarHostState = snackbarHostState)
    }
    composable("${NavigationRoutes.CategoryRoute.route}{${NavigationRoutes.CategoryRoute.idRoute}}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString(NavigationRoutes.CategoryRoute.idRoute)?.toInt() ?: -1
        onNavigate(NavigationRoutes.CategoryRoute.route)
        CategoryScreen(navController = navController, id = id)
    }
    composable("${NavigationRoutes.ProductRoute.route}{${NavigationRoutes.ProductRoute.idRoute}}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString(NavigationRoutes.ProductRoute.idRoute)?.toInt() ?: -1
        onNavigate(NavigationRoutes.ProductRoute.route)
        ProductScreen(navController = navController, id = id, favoritesState = favoritesState)
    }
}