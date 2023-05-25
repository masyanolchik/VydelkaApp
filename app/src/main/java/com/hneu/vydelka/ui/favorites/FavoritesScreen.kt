package com.hneu.vydelka.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hneu.core.domain.order.Cart
import com.hneu.core.domain.product.Product
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.feed.components.ProductCard
import com.hneu.vydelka.ui.navigation.NavigationRoutes
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.accountmanager.AccountManagerImpl
import com.hneu.vydelka.ui.feed.FeedViewModel

@Composable
fun Favorites(
    cart: Cart = AccountManagerImpl.CART_UNREGISTERED_USER,
    navController: NavHostController = rememberNavController(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    feedViewModel: FeedViewModel = hiltViewModel(),
    ) {
    val productsState by favoritesViewModel.favoritesProducts.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        ){
            val (sumTextRef, buttonRef) = createRefs()
            val productTotalSum by rememberSaveable{
                var totalSum = 0
                when(productsState) {
                    is Result.Success -> {
                        (productsState as Result.Success<List<Product>>).data.forEach {
                            totalSum += it.price.toInt()
                        }
                    }
                    else -> {}
                }
                mutableStateOf(totalSum)
            }
            Text(
                text = stringResource(id = R.string.favorites_total_sum, productTotalSum),
                modifier = Modifier
                    .constrainAs(sumTextRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(buttonRef.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            )
                Button(
                    onClick = { },
                    modifier = Modifier
                        .constrainAs(buttonRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(
                        text = stringResource(id = R.string.add_to_cart_button_label),
                    )
                }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when(productsState) {
                is Result.Success -> {
                    val products = (productsState as Result.Success<List<Product>>).data
                    items(products) {
                        var isProductAddedToCart by rememberSaveable {
                            mutableStateOf(
                                cart.orderedProducts.map {op ->
                                    op.product.id
                                }.contains(it.id)
                            )
                        }
                        var isProductFavorited by rememberSaveable {  mutableStateOf(products.map { it.id }.contains(it.id)) }
                        ProductCard(
                            title = it.name,
                            price = "${it.price.toPlainString()} ₴",
                            contentDescription = it.name,
                            imageSrc = it.titleImageSrc,
                            isProductFavorited = isProductFavorited,
                            isProductAddedToCart = isProductAddedToCart,
                            onFavoriteButtonClicked = {
                                isProductFavorited = !isProductFavorited
                                if(isProductFavorited) {
                                    feedViewModel.addProductToFavorites(it)
                                } else {
                                    feedViewModel.removeProductFromFavorites(it)
                                }
                            },
                            onCartButtonClicked = {
                                isProductAddedToCart = !isProductAddedToCart
                                if(isProductAddedToCart) {
                                    feedViewModel.addProductToCart(it)
                                } else {
                                    feedViewModel.removeProductFromCart(it)
                                }
                            },
                        ) {
                            navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, it.id))
                        }
                    }
                }
                else -> {}
            }

        }
    }
}

data class DummyProduct(
    val name: String,
    val price: String,
    val imageSrc: String,
    val contentDescription: String,
)

@Preview(showBackground = true)
@Composable
fun FavoritesPreview() {
    Favorites()
}