package com.hneu.vydelka.ui.feed

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.hneu.core.domain.order.Cart
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.promo.Promo
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.feed.components.*
import com.hneu.vydelka.ui.feed.promo.PromoScreen
import com.hneu.vydelka.ui.navigation.NavigationRoutes
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.accountmanager.AccountManagerImpl
import com.hneu.vydelka.ui.categories.CategoriesViewModel
import com.hneu.vydelka.ui.favorites.FavoritesViewModel

@Composable
fun Feed(
    cart: Cart = AccountManagerImpl.CART_UNREGISTERED_USER,
    navController: NavHostController = rememberNavController(),
    favoritesState: Result<List<Product>> = Result.Completed(),
    feedViewModel: FeedViewModel = hiltViewModel(),
    categoriesViewModel: CategoriesViewModel = hiltViewModel(),
) {
    var openPromoScreenDialog by rememberSaveable { mutableStateOf(false) }
    var openPromoScreenId by rememberSaveable { mutableStateOf(0) }

    if(openPromoScreenDialog) {
        PromoScreen(promoId = openPromoScreenId) {
           openPromoScreenDialog = !openPromoScreenDialog
        }
    } else {
        val promoListState by feedViewModel.promosStateFlow.collectAsStateWithLifecycle()
        val catToShow = remember { mutableStateListOf<CategoriesViewModel.CategoryNode>() }
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            item {
                when(promoListState) {
                    is Result.Loading -> CircularProgressIndicator()
                    is Result.Success -> {
                        val rememberedList = remember { (promoListState as Result.Success<List<Promo>>).data }
                        val rememberedAdapter = remember(openPromoScreenDialog, openPromoScreenId) {
                            CarouselImageAdapter() {
                                openPromoScreenDialog = !openPromoScreenDialog
                                openPromoScreenId = it.promoId
                            }.apply {
                                submitList(
                                    rememberedList.map {
                                        CarouselImageAdapter
                                            .CarouselData(it.name, it.titleImageSrc, it.id)
                                    }
                                )
                            }
                        }
                        AndroidView(
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(196.dp)
                                .padding(vertical = 8.dp),
                            factory = { context -> RecyclerView(context).apply {
                                layoutManager = CarouselLayoutManager()
                                adapter = rememberedAdapter
                                clipChildren = false
                                clipToPadding = false
                            } },

                        )
                    }
                    else -> { }
                }

                val categoryList by categoriesViewModel.categoryList.collectAsState()
                when(categoryList) {
                    is Result.Loading -> CircularProgressIndicator()
                    is Result.Success -> {
                        val categoryListFiltered = (categoryList as Result.Success<List<CategoriesViewModel.CategoryNode>>).data
                            .filter { categoryNode -> categoryNode.category.attributeGroups.isNotEmpty() }
                            .take(8)
                        if(!catToShow.containsAll(categoryListFiltered)) {
                            catToShow.addAll(categoryListFiltered)
                        }
                        val rowCount = if(catToShow.size > 4) 2 else 1
                        for (i in 0 until rowCount) {
                            val itemsInRow = if(i == 0) catToShow.take(4) else catToShow.takeLast(4)
                            LazyRow (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                            ){
                                items(itemsInRow) {
                                    SmallCategoryButton(it.category.name, it.categoryIcon, it.category.name) {
                                        navController.navigate(
                                            NavigationRoutes.getNavigationRoute(NavigationRoutes.CategoryRoute, it.category.id)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    else -> { }
                }
                Divider()
                // Placeholder for a real section in the future
                val topProducts by feedViewModel.topProducts.collectAsStateWithLifecycle()
                when(topProducts) {
                    is Result.Loading -> CircularProgressIndicator()
                    is Result.Success -> {
                        SectionLabel(text = stringResource(id = R.string.feed_best_section))
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                        ) {
                            val data = (topProducts as Result.Success<List<Product>>).data
                            items(data) {
                                var isProductAddedToCart by rememberSaveable(cart) {
                                    mutableStateOf(
                                        cart.orderedProducts.map {op ->
                                            op.product.id
                                        }.contains(it.id)
                                    )
                                }
                                MediumBoxProductCard(
                                    title = it.name,
                                    price = "${it.price.toPlainString()} ₴",
                                    imageSrc = it.titleImageSrc,
                                    contentDescription = it.name,
                                    isProductAddedToCart = isProductAddedToCart,
                                    onCardClicked = {
                                        navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, it.id))
                                    },
                                    onCartAddedClicked = {
                                        isProductAddedToCart = !isProductAddedToCart
                                        if(isProductAddedToCart) {
                                            feedViewModel.addProductToCart(it)
                                        } else {
                                            feedViewModel.removeProductFromCart(it)
                                        }
                                    },)
                            }
                        }
                    }
                    else -> { }
                }
                Divider()
                val isProductWatchHistoryEnabled by feedViewModel.isUserLoggedIn.collectAsStateWithLifecycle(initialValue = false)
                if(isProductWatchHistoryEnabled) {
                    SectionLabel(text = stringResource(id = R.string.feed_history_section))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                    ) {
                        SmallBoxProduct(
                            title = "Product Name Pro Max 256/16 GB",
                            price = "9 999 ₴",
                            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                            contentDescription = "",
                        ) {
                            navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                        }
                        SmallBoxProduct(
                            title = "Product Name Pro Max 256/16 GB",
                            price = "9 999 ₴",
                            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                            contentDescription = "",
                        ) {
                            navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                        }
                        SmallBoxProduct(
                            title = "Product Name Pro Max 256/16 GB",
                            price = "9 999 ₴",
                            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                            contentDescription = "",
                        ) {
                            navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                        }
                        SmallBoxProduct(
                            title = "Product Name Pro Max 256/16 GB",
                            price = "9 999 ₴",
                            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                            contentDescription = "",
                        ) {
                            navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                        }
                        SmallBoxProduct(
                            title = "Product Name Pro Max 256/16 GB",
                            price = "9 999 ₴",
                            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                            contentDescription = "",
                        ) {
                            navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                        }
                    }
                    Divider()
                }
            }

            if(catToShow.isNotEmpty()) {
                items(catToShow) {
                    feedViewModel.fetchProductsByCategoryId(it.category.id)
                    val products by feedViewModel.categoryToProductsMap.collectAsStateWithLifecycle()
                    when(products[it.category.id]) {
                        is Result.Loading -> CircularProgressIndicator()
                        is Result.Success -> {
                            val productList = remember { ((products[it.category.id] as Result.Success<List<Product>>).data).toMutableStateList() }
                            SectionLabel(text = it.category.name)
                            productList.forEach {
                                var isProductAddedToCart = cart.orderedProducts.map {op -> op.product.id }.contains(it.id)
                                var isProductFavorited =
                                    when(favoritesState) {
                                        is Result.Success ->  (favoritesState as Result.Success<List<Product>>).data.map { it.id }.contains(it.id)
                                        else -> false
                                    }

                                ProductCard(
                                    title = it.name,
                                    price = "${it.price.toPlainString()} ₴",
                                    contentDescription = it.name,
                                    imageSrc = it.titleImageSrc,
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
                                    isProductFavorited = isProductFavorited,
                                    isProductAddedToCart = isProductAddedToCart,
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
    }
}

@Preview(showBackground = true)
@Composable
fun FeedPreview() {
    Feed()
}