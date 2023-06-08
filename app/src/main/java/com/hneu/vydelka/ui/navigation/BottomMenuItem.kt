package com.hneu.vydelka.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.hneu.core.domain.product.Tag
import com.hneu.vydelka.R


sealed class BottomMenuItem(val route: String, val notSelectedIcon: ImageVector, val selectedIcon: ImageVector, @StringRes val titleId: Int) {
    object FeedScreen : BottomMenuItem("feed_screen", Icons.Outlined.Home, Icons.Filled.Home, R.string.bottom_bar_home)
    object CatalogueScreen : BottomMenuItem("catalogue_screen", Icons.Outlined.Category, Icons.Filled.Category, R.string.bottom_bar_catalogue)
    object FavoritesScreen : BottomMenuItem("favorites_screen", Icons.Outlined.Favorite, Icons.Filled.Favorite, R.string.bottom_bar_favorites)
    object ProfileScreen : BottomMenuItem("profile_screen", Icons.Outlined.Person, Icons.Filled.Person, R.string.bottom_bar_profile)
}
