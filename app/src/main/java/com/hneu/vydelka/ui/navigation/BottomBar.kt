package com.hneu.vydelka.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomMenu(navController: NavHostController, menuItems: List<BottomMenuItem>) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        menuItems.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onTertiary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.tertiary
                ),
                selected = selected,
                onClick = { navController.navigate(item.route) },
                label = {
                    Text(
                        text = stringResource(id = item.titleId),
                    )
                },
                icon = {
                    Icon(
                        imageVector = if(selected) item.selectedIcon else item.notSelectedIcon,
                        contentDescription = stringResource(id = item.titleId)
                    )
                }
            )
        }
    }
}