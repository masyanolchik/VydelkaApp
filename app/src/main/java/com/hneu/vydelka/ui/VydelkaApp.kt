package com.hneu.vydelka.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hneu.core.domain.order.Order
import com.hneu.vydelka.ui.favorites.FavoritesViewModel
import com.hneu.vydelka.ui.navigation.*
import com.hneu.vydelka.ui.order.CartBottomSheetContent
import com.hneu.vydelka.ui.order.OrderConfirmationForm
import com.hneu.vydelka.ui.order.OrderViewModel
import kotlinx.coroutines.launch
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn

@Composable
fun VydelkaApp() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController = navController, scrollState = scrollState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    orderViewModel: OrderViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
) {
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
    val cart by orderViewModel.cartFlow.collectAsStateWithLifecycle()
    val favoriteProducts by favoritesViewModel.favoritesProducts.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            if(showMainTopAppbar) {
                MainTopBar(
                    showLogo = showLogo,
                    showSearch = showSearchTextField,
                    badgeNumber = cart.orderedProducts.size
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
        NavigationWrapper(navController,cart,favoriteProducts, scrollState, it) { route ->
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
        val orderCreatedFlow by orderViewModel.orderCreatedStatusFlow.collectAsStateWithLifecycle()
        when(orderCreatedFlow) {
            is Result.Success -> {
                LaunchedEffect(orderCreatedFlow) {
                    snackbarHostState.showSnackbar("Замовлення ${(orderCreatedFlow as Result.Success<Order>).data.id} створено",
                        duration = SnackbarDuration.Long)
                }
            }
            else -> {}
        }
        if(showOrderFormDialog) {
            var nameText by rememberSaveable { mutableStateOf("") }
            var lastNameText by rememberSaveable { mutableStateOf("") }
            var phoneText by rememberSaveable { mutableStateOf("") }
            var addressText by rememberSaveable { mutableStateOf("") }

            OrderConfirmationForm(
                nameText = nameText,
                lastNameText = lastNameText,
                phoneText = phoneText,
                addressText = addressText,
                onNameTextFieldValueChanged = { text -> nameText = text},
                onLastNameTextFieldValueChanged = { text -> lastNameText = text},
                onPhoneTextFieldValueChanged = { text -> phoneText = text},
                onAddressTextFieldValueChanged = { text -> addressText = text},
                onClose = {
                    showOrderFormDialog = false

                },
                onProceed = {
                    showOrderFormDialog = false
                    orderViewModel
                        .saveOrder(nameText, lastNameText, phoneText, addressText)
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

