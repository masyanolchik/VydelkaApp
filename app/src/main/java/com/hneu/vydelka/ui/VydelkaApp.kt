package com.hneu.vydelka.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hneu.vydelka.ui.navigation.*
import com.hneu.vydelka.ui.order.CartBottomSheetContent
import com.hneu.vydelka.ui.order.OrderConfirmationForm
import kotlinx.coroutines.launch

@Composable
fun VydelkaApp() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController = navController, scrollState = scrollState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showMainTopAppbar by rememberSaveable { mutableStateOf(true) }
    var showBottomMenu by rememberSaveable { mutableStateOf(true) }
    var showLogo by rememberSaveable() { mutableStateOf(true) }
    var showSearchTextField by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    var showOrderFormDialog by rememberSaveable{ mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    Scaffold(
        topBar = {
            if(showMainTopAppbar) {
                MainTopBar(
                    showLogo = showLogo,
                    showSearch = showSearchTextField,
                ) {
                    openBottomSheet = !openBottomSheet
                }
            }
        },
        bottomBar = {
            if(showBottomMenu) {
                BottomMenu(navController = navController)
            }
        },
    ) {
        NavigationWrapper(navController, scrollState, it) { route ->
            when {
                route == BottomMenuItem.FeedScreen.route -> {
                    showMainTopAppbar = true
                    showLogo = true
                    showSearchTextField = false
                    showBottomMenu = true
                }
                route == BottomMenuItem.CatalogueScreen.route -> {
                    showMainTopAppbar = true
                    showLogo = false
                    showSearchTextField = true
                    showBottomMenu = true
                }
                route == BottomMenuItem.FavoritesScreen.route -> {
                    showMainTopAppbar = true
                    showLogo = true
                    showSearchTextField = false
                    showBottomMenu = true
                }
                route == BottomMenuItem.ProfileScreen.route -> {
                    showMainTopAppbar = false
                    showLogo = false
                    showSearchTextField = false
                    showBottomMenu = true
                }
                route.contains(NavigationRoutes.CategoryRoute.route) -> {
                    showMainTopAppbar = false
                    showLogo = false
                    showSearchTextField = false
                    showBottomMenu = false
                }
                route.contains(NavigationRoutes.ProductRoute.route) -> {
                    showMainTopAppbar = false
                    showLogo = false
                    showSearchTextField = false
                    showBottomMenu = false
                }
            }
        }
        if(showOrderFormDialog) {
            OrderConfirmationForm(
                onClose = {
                    showOrderFormDialog = false

                },
                onProceed = {
                    showOrderFormDialog = false
                }
            )
        }
        if(openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
            ) {
                CartBottomSheetContent {
                    scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) {
                            openBottomSheet = false
                            showOrderFormDialog = true
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    VydelkaApp()
}

