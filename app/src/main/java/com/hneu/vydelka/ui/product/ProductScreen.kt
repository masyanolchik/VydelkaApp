package com.hneu.vydelka.ui.product

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.RemoveShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.hneu.core.domain.order.Cart
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.feed.CarouselImageAdapter
import com.hneu.vydelka.ui.order.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navController: NavController = rememberNavController(),
    favoritesState: Result<List<Product>> = Result.Completed(),
    orderViewModel: OrderViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel(),
    id: Int = 0,
) {
    val carouselImageAdapter = CarouselImageAdapter(true) {}
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val cart by orderViewModel.cartFlow.collectAsStateWithLifecycle()
    var isProductAddedToCart by rememberSaveable {
        mutableStateOf(
            cart.orderedProducts.map {op ->
                op.product.id
            }.contains(id)
        )
    }
    var isProductFavorite by rememberSaveable { mutableStateOf(when(favoritesState) {
        is Result.Success ->  favoritesState.data.map { it.id }.contains(id)
        else -> false
    }) }
    val topAppBarNavigationIcon: @Composable () -> Unit = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = stringResource(id = R.string.top_app_bar_dismiss_icon_description)
            )
        }
    }
    var onAddProductClickCallback = {}
    var onAddFavoriteClickCallback = {}
    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = topAppBarNavigationIcon,
                title = {},
                actions = {
                    IconButton(onClick = onAddFavoriteClickCallback) {
                        if(isProductFavorite) {
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = stringResource(id = R.string.add_to_favorites_button_label)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = stringResource(id = R.string.add_to_favorites_button_label)
                            )
                        }

                    }
                },
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddProductClickCallback,
                expanded = false,
                icon = {
                    if(isProductAddedToCart) {
                        Icon(
                            imageVector = Icons.Outlined.RemoveShoppingCart,
                            contentDescription = stringResource(id = R.string.add_to_cart_button_label),
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.AddShoppingCart,
                            contentDescription = stringResource(id = R.string.add_to_cart_button_label),
                        )
                    }

                },
                text = {
                   Text(stringResource(R.string.add_to_cart_button_label))
                },
            )
        }
    ) {
        val product by productViewModel.productStateFlow.collectAsStateWithLifecycle()
        productViewModel.getProductById(id)
        when(product) {
            is Result.Success -> {
                val productObject = (product as Result.Success<Product>).data
                onAddProductClickCallback = {
                        isProductAddedToCart = !isProductAddedToCart
                        if(isProductAddedToCart) {
                            orderViewModel.addProductToCart(productObject)
                        } else {
                            orderViewModel.removeProductFromCart(productObject)
                        }
                }
                onAddFavoriteClickCallback = {
                    isProductFavorite = !isProductFavorite
                    if(isProductFavorite) {
                        orderViewModel.addProductToFavorites(productObject)
                    } else {
                        orderViewModel.removeProductFromFavorites(productObject)
                    }
                }
                LazyColumn(modifier = Modifier.padding(it)) {
                    item {
                        AndroidView(
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .padding(horizontal = 16.dp),
                            factory = { context -> RecyclerView(context).apply {
                                layoutManager = CarouselLayoutManager()
                                adapter = carouselImageAdapter
                                clipChildren = false
                                clipToPadding = false
                            } },
                            update = {
                                carouselImageAdapter.submitList(productObject.images.map {
                                    CarouselImageAdapter.CarouselData(
                                        productObject.name,
                                        it,
                                        productObject.id
                                    )
                                })
                            },
                        )
                        Text(
                            text = stringResource(R.string.product_code_label, productObject.id),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.4.sp,
                        )
                        Text(
                            text = productObject.name,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp, end = 16.dp),
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.25.sp,
                            style = LocalTextStyle.current.copy(lineHeight = 34.sp),
                        )
                        Text(
                            text = stringResource(R.string.product_price_label,productObject.price.toInt()),
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            style = LocalTextStyle.current.copy(lineHeight = 24.sp),
                        )
                        Text(
                            text = productObject.status,
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                        )
                        Text(
                            text = stringResource(R.string.product_warranty_label,productObject.warrantyLengthMonth),
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                        )
                        Text(
                            text = stringResource(R.string.product_return_label,productObject.returnExchangeLength),
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                        )

                        Divider(
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                        )
                        Text(
                            text = stringResource(R.string.product_characteristics_label),
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            style = LocalTextStyle.current.copy(lineHeight = 24.sp),
                        )
                    }
                    items(productObject.attributes.keys.toList()) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)) {
                            Text(
                                text = it.name,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .weight(0.5f),
                            )
                            Text(
                                text = productObject.attributes[it]?.name ?: "",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .weight(0.5f),
                            )
                        }
                    }

                    item {
                        Divider(
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                        )
                        Text(
                            text = stringResource(R.string.product_description_label),
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            style = LocalTextStyle.current.copy(lineHeight = 24.sp),
                        )
                        Text(
                            text = productObject.description,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

            }
            else -> {}
        }
    }
}