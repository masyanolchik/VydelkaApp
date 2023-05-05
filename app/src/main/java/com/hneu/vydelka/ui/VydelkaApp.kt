package com.hneu.vydelka.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.feed.components.CartProductCard
import com.hneu.vydelka.ui.navigation.BottomMenu
import com.hneu.vydelka.ui.navigation.NavigationWrapper
import com.hneu.vydelka.ui.navigation.TopBar
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
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    Scaffold(
        topBar = {
            TopBar {
                openBottomSheet = !openBottomSheet
            }
        },
        bottomBar = {
            BottomMenu(navController = navController)
        },
    ) {
        NavigationWrapper(navController, scrollState, it)
        if(openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .weight(0.9f)
                    ) {
                        CartProductCard(
                            title = "Product Name Pro Max 256/16 GB",
                            price = "9 999 ₴",
                            contentDescription = "",
                            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                        )
                        CartProductCard(
                            title = "Product Name Pro Max 256/16 GB",
                            price = "9 999 ₴",
                            contentDescription = "",
                            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                        )
                        CartProductCard(
                            title = "Product Name Pro Max 256/16 GB",
                            price = "9 999 ₴",
                            contentDescription = "",
                            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                        )
                        CartProductCard(
                            title = "Product Name Pro Max 256/16 GB",
                            price = "9 999 ₴",
                            contentDescription = "",
                            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                        )
                        CartProductCard(
                            title = "Product Name Pro Max 256/16 GB",
                            price = "9 999 ₴",
                            contentDescription = "",
                            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                        )
                    }
                    Button(
                        // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                        // you must additionally handle intended state cleanup, if any.
                        onClick = {
                            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) {
                                    openBottomSheet = false
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(0.1f)
                            .fillMaxWidth(),
                    ) {
                        Text(stringResource(id = R.string.bottom_sheet_proceed_order))
                    }
                }

            }
        }
    }
}
