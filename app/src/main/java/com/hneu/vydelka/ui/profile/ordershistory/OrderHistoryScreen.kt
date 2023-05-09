package com.hneu.vydelka.ui.profile.ordershistory

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
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.favorites.DummyProduct
import com.hneu.vydelka.ui.order.components.FullScreenDialogWithElevatedTopAppBar
import com.hneu.vydelka.ui.profile.ordershistory.components.DummyOrder
import com.hneu.vydelka.ui.profile.ordershistory.components.OrderCard

@Composable
fun OrderHistoryScreen(onClose: () -> Unit) {
    val topAppBarNavigationIcon: @Composable () -> Unit = {
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(id = R.string.top_app_bar_dismiss_icon_description),
            )
        }
    }
    val topAppBarTitle: @Composable () -> Unit = {
        Text(stringResource(id = R.string.profile_my_orders))
    }

    FullScreenDialogWithElevatedTopAppBar(
        topAppBarTitle = topAppBarTitle,
        topAppBarNavigationIcon = topAppBarNavigationIcon,
    ) {
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
        )
        val orderHistory = listOf(
            DummyOrder(
                "239849238942",
                "В роботі",
                "21.08.2020 08:20",
                "110 000 ₴",
                productList,
            ),
            DummyOrder(
                "239849238942",
                "В роботі",
                "21.08.2020 08:20",
                "110 000 ₴",
                productList,
            ),
            DummyOrder(
                "239849238942",
                "В роботі",
                "21.08.2020 08:20",
                "110 000 ₴",
                productList,
            ),
            DummyOrder(
                "239849238942",
                "В роботі",
                "21.08.2020 08:20",
                "110 000 ₴",
                productList,
            ),
            DummyOrder(
                "239849238942",
                "В роботі",
                "21.08.2020 08:20",
                "110 000 ₴",
                productList,
            ),
            DummyOrder(
                "239849238942",
                "В роботі",
                "21.08.2020 08:20",
                "110 000 ₴",
                productList,
            ),
            DummyOrder(
                "239849238942",
                "В роботі",
                "21.08.2020 08:20",
                "110 000 ₴",
                productList,
            ),
            DummyOrder(
                "239849238942",
                "В роботі",
                "21.08.2020 08:20",
                "110 000 ₴",
                productList,
            ),
            DummyOrder(
                "239849238942",
                "В роботі",
                "21.08.2020 08:20",
                "110 000 ₴",
                productList,
            ),
            DummyOrder(
                "239849238942",
                "В роботі",
                "21.08.2020 08:20",
                "110 000 ₴",
                productList,
            ),
            DummyOrder(
                "239849238942",
                "В роботі",
                "21.08.2020 08:20",
                "110 000 ₴",
                productList,
            ),
        )
        LazyColumn(
            modifier = Modifier.padding(it),
        ) {
            items(orderHistory) {
                OrderCard(
                    it.orderNumber,
                    it.orderStatus,
                    it.orderDate,
                    it.orderTotalSum,
                    it.productList,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderHistory() {
    OrderHistoryScreen {}
}