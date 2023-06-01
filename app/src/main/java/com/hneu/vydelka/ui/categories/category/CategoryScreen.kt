package com.hneu.vydelka.ui.categories.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hneu.core.domain.order.Cart
import com.hneu.core.domain.product.Product
import com.hneu.vydelka.ui.categories.CategoriesViewModel
import com.hneu.vydelka.ui.feed.components.ProductCard
import com.hneu.vydelka.ui.navigation.CategoryTopAppBar
import com.hneu.vydelka.ui.navigation.NavigationRoutes
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.accountmanager.AccountManagerImpl
import com.hneu.vydelka.ui.favorites.FavoritesViewModel

@Composable
fun CategoryScreen(
    navController: NavController = rememberNavController(),
    id: Int = 0,
    categoriesViewModel: CategoriesViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
) {
    val products by categoriesViewModel.fetchProductsByCategoryId(id).collectAsStateWithLifecycle()
    val categoryResult by categoriesViewModel.categoryList.collectAsStateWithLifecycle()
    val favoriteProducts by favoritesViewModel.favoritesProducts.collectAsStateWithLifecycle()
    var categoryTitle by remember { mutableStateOf("") }
    when(categoryResult) {
        is Result.Success -> {
            (categoryResult as Result.Success<List<CategoriesViewModel.CategoryNode>>).data.find {
                it.category.id == id
            }?.let {
                categoryTitle = it.category.name
            }
        }
        else -> {}
    }
    val cart by categoriesViewModel.cart.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CategoryTopAppBar(
                badgeNumber = cart.orderedProducts.size,
                title = categoryTitle,
                onClose = { navController.popBackStack() }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            when(products) {
                is Result.Success -> {
                    val productList = (products as Result.Success<List<Product>>).data
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(productList) {
                            var isProductAddedToCart by rememberSaveable {
                                mutableStateOf(
                                    cart.orderedProducts.map {op ->
                                        op.product.id
                                    }.contains(it.id)
                                )
                            }
                            var isProductFavorited by rememberSaveable { mutableStateOf(when(favoriteProducts) {
                                is Result.Success ->  (favoriteProducts as Result.Success<List<Product>>).data.map { it.id }.contains(it.id)
                                else -> false
                            })
                            }
                            ProductCard(
                                title = it.name,
                                price = it.price.toPlainString(),
                                contentDescription = "",
                                imageSrc = it.titleImageSrc,
                                onFavoriteButtonClicked = {
                                    isProductFavorited = !isProductFavorited
                                    if(isProductFavorited) {
                                        categoriesViewModel.addProductToFavorites(it)
                                    } else {
                                        categoriesViewModel.removeProductFromFavorites(it)
                                    }
                                },
                                onCartButtonClicked = {
                                    isProductAddedToCart = !isProductAddedToCart
                                    if(isProductAddedToCart) {
                                        categoriesViewModel.addProductToCart(it)
                                    } else {
                                        categoriesViewModel.removeProductFromCart(it)
                                    }
                                },
                                isProductAddedToCart = isProductAddedToCart,
                                isProductFavorited = isProductFavorited,
                            ) {
                                navController.navigate(
                                    NavigationRoutes.getNavigationRoute(
                                        NavigationRoutes.ProductRoute,
                                        it.id
                                    )
                                )
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }
}