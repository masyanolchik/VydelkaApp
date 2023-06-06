package com.hneu.vydelka.ui.order

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.feed.components.CartProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartBottomSheetContent(
    orderViewModel: OrderViewModel = hiltViewModel(),
    onProceedOrderClick: () -> Unit = {},
) {
    Column(modifier = Modifier
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        val cart by orderViewModel.cartFlow.collectAsStateWithLifecycle()
        LazyColumn(
            modifier = Modifier
                .weight(0.9f)
        ) {
            items(cart.orderedProducts) {
                var quantity by rememberSaveable { mutableStateOf(it.quantity) }
                var isProductAddedToCart by rememberSaveable(cart) {
                    mutableStateOf(
                        cart.orderedProducts.map {op ->
                            op.product.id
                        }.contains(it.id)
                    )
                }
                CartProductCard(
                    title = it.product.name,
                    price = "${it.product.price.toPlainString()} ₴",
                    contentDescription = it.product.name,
                    imageSrc = it.product.titleImageSrc,
                    quantity = quantity,
                    onQuantityChanges = {
                        try {
                            quantity = it.toInt()
                        } catch (e: Exception) {
                            e.toString()
                        }
                    },
                    isProductAddedToFavorite = isProductAddedToCart,
                    onFavoriteButtonClicked = {
                        orderViewModel.addProductToFavorites(it.product)
                    },
                    onDecreaseQuantityClick = {
                        quantity--
                        orderViewModel.changeProductQuantityInCart(it.product, quantity)
                    },
                    onIncreaseQuantityClick = {
                        quantity++
                        orderViewModel.changeProductQuantityInCart(it.product, quantity)
                    },
                    onDeleteProduct = {
                        orderViewModel.removeProductFromCart(it.product)
                    },
                )
            }
        }
        if(cart.orderedProducts.isNotEmpty()) {
            Button(
                onClick = {
                    onProceedOrderClick()
                },
                modifier = Modifier
                    .padding(16.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
            ) {
                Text(stringResource(id = R.string.bottom_sheet_proceed_order))
            }
        } else {
            Text(
                text = stringResource(id = R.string.bottom_sheet_empty),
                modifier = Modifier
                    .padding(16.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

    }
}