package com.hneu.vydelka.ui.navigation

import androidx.annotation.StringRes

sealed class NavigationRoutes private constructor(val route: String, val idRoute: String) {
    object CategoryRoute : NavigationRoutes("category/", "categoryId")
    object ProductRoute : NavigationRoutes("product/", "productId")

    companion object {
        fun getNavigationRoute(navRoute: NavigationRoutes, id: Int) = "${navRoute.route}$id"
    }
}
