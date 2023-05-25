package com.hneu.vydelka.ui.categories.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun CategoryScreen(
    navController: NavController = rememberNavController(),
    id: Int = 0,
    categoriesViewModel: CategoriesViewModel = hiltViewModel(),
) {
    val products by categoriesViewModel.fetchProductsByCategoryId(id).collectAsStateWithLifecycle()
    val categoryResult by categoriesViewModel.categoryList.collectAsStateWithLifecycle()
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
                            ProductCard(
                                title = it.name,
                                price = it.price.toPlainString(),
                                contentDescription = "",
                                imageSrc = it.titleImageSrc,
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