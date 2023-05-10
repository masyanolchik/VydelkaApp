package com.hneu.vydelka.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hneu.vydelka.ui.navigation.BottomMenu
import com.hneu.vydelka.ui.navigation.BottomMenuItem
import com.hneu.vydelka.ui.navigation.NavigationWrapper
import com.hneu.vydelka.ui.navigation.TopBar
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
    var showTopAppbar by rememberSaveable { mutableStateOf(true) }
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
            if(showTopAppbar) {
                TopBar(
                    showLogo = showLogo,
                    showSearch = showSearchTextField,
                ) {
                    openBottomSheet = !openBottomSheet
                }
            }
        },
        bottomBar = {
            BottomMenu(navController = navController)
        },
    ) {
        NavigationWrapper(navController, scrollState, it) { route ->
            showTopAppbar = route != BottomMenuItem.ProfileScreen.route
            showLogo = route != BottomMenuItem.CatalogueScreen.route
            showSearchTextField = route == BottomMenuItem.CatalogueScreen.route
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

