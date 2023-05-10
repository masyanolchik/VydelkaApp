package com.hneu.vydelka.ui.profile.viewhistory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.favorites.DummyProduct
import com.hneu.vydelka.ui.feed.components.ProductCard
import com.hneu.vydelka.ui.navigation.NavigationRoutes
import com.hneu.vydelka.ui.order.components.FullScreenDialogWithElevatedTopAppBar

@Composable
fun ViewHistoryScreen(navController: NavHostController = rememberNavController(), onClose: () -> Unit) {
    val topAppBarNavigationIcon: @Composable () -> Unit = {
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(id = R.string.top_app_bar_dismiss_icon_description),
            )
        }
    }
    val topAppBarTitle: @Composable () -> Unit = {
        Text(stringResource(id = R.string.view_history_title))
    }

    val productList = listOf(
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
    )

    FullScreenDialogWithElevatedTopAppBar(
        topAppBarNavigationIcon = topAppBarNavigationIcon,
        topAppBarTitle = topAppBarTitle
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productList) {
                    ProductCard(
                        title = it.name,
                        price = it.price,
                        contentDescription =it.contentDescription,
                        imageSrc = it.imageSrc,
                    ) {
                        navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewViewHistory() {
    ViewHistoryScreen {

    }
}