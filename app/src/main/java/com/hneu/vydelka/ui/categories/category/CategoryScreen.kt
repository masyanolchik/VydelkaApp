package com.hneu.vydelka.ui.categories.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hneu.core.domain.product.Product
import com.hneu.vydelka.ui.categories.CategoriesViewModel
import com.hneu.vydelka.ui.feed.components.ProductCard
import com.hneu.vydelka.ui.navigation.CategoryTopAppBar
import com.hneu.vydelka.ui.navigation.NavigationRoutes
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.categories.category.components.FilterBottomSheet
import com.hneu.vydelka.ui.categories.category.components.SortBottomSheet
import com.hneu.vydelka.ui.favorites.FavoritesViewModel
import com.hneu.vydelka.ui.order.CartBottomSheetContent
import com.hneu.vydelka.ui.order.OrderConfirmationForm
import com.hneu.vydelka.ui.order.OrderViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavController = rememberNavController(),
    id: Int = 0,
    categoriesViewModel: CategoriesViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel(),
) {
    val products by categoryViewModel.fetchSortedProductsByCategoryId(id).collectAsStateWithLifecycle()
    val minBoundPrice by categoryViewModel.minBoundPrice.collectAsStateWithLifecycle()
    val maxBoundPrice by categoryViewModel.maxBoundPrice.collectAsStateWithLifecycle()
    var minProductPrice by rememberSaveable {
        mutableStateOf(BigDecimal(0))
    }
    var maxProductPrice by rememberSaveable {
        mutableStateOf(BigDecimal(0))
    }
    val currentCategory by categoriesViewModel.getCategoryById(id).collectAsStateWithLifecycle()
    val favoriteProducts by favoritesViewModel.favoritesProducts.collectAsStateWithLifecycle()
    val bottomSheetOpenedState by categoryViewModel.bottomSheetState.collectAsStateWithLifecycle()
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    var showOrderFormDialog by rememberSaveable{ mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    val cart by categoriesViewModel.cart.collectAsStateWithLifecycle()
    val currentSortingMethod by categoryViewModel.currentSortingMethod.collectAsStateWithLifecycle()
    val currentAppliedFilterMethod by categoryViewModel.currentAppliedFilter.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CategoryTopAppBar(
                badgeNumber = cart.orderedProducts.size,
                title = currentCategory?.name ?: "",
                onClose = { navController.popBackStack() },
                onSortButtonClick = { categoryViewModel.changeBottomSheetState(CategoryViewModel.CategoryScreenBottomSheetState.SORTING) },
                onFilterButtonClick = {categoryViewModel.changeBottomSheetState(CategoryViewModel.CategoryScreenBottomSheetState.FILTER)},
                onCartButtonClick = { categoryViewModel.changeBottomSheetState(CategoryViewModel.CategoryScreenBottomSheetState.CART) }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            when(products) {
                is Result.Success -> {
                    val productList = (products as Result.Success<List<Product>>).data
                    if(productList.isNotEmpty()) {
                        minProductPrice = productList.minBy { it.price }.price
                        maxProductPrice = productList.maxBy { it.price }.price
                    }
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
        val scope = rememberCoroutineScope()
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
        when(bottomSheetOpenedState) {
            CategoryViewModel.CategoryScreenBottomSheetState.COLLAPSED -> {}
            CategoryViewModel.CategoryScreenBottomSheetState.SORTING -> {
                ModalBottomSheet(
                    onDismissRequest = { categoryViewModel.changeBottomSheetState(CategoryViewModel.CategoryScreenBottomSheetState.COLLAPSED) },
                    sheetState = bottomSheetState,
                ) {
                    SortBottomSheet(
                        title = stringResource(id = R.string.catalogue_sort_button_description),
                        currentSelectedMethod = currentSortingMethod,
                        onSortOptionSelected = {
                            scope.launch {
                                categoryViewModel.fetchSortedProductsByCategoryId(id,it)
                                bottomSheetState.hide()
                                categoryViewModel.changeBottomSheetState(CategoryViewModel.CategoryScreenBottomSheetState.COLLAPSED)
                            }
                        },
                        onDismissButtonClicked = {
                            scope.launch {
                                bottomSheetState.hide()
                                categoryViewModel.changeBottomSheetState(CategoryViewModel.CategoryScreenBottomSheetState.COLLAPSED)
                            }
                        },
                    )
                }
            }
            CategoryViewModel.CategoryScreenBottomSheetState.FILTER -> {
                ModalBottomSheet(
                    onDismissRequest = { categoryViewModel.changeBottomSheetState(CategoryViewModel.CategoryScreenBottomSheetState.COLLAPSED) },
                    sheetState = bottomSheetState,
                ) {
                    FilterBottomSheet(
                        title = stringResource(id = R.string.apply_filters),
                        priceBoundStart = minBoundPrice,
                        priceBoundEnd = maxBoundPrice,
                        attributesGroups = currentCategory?.attributeGroups ?: emptyList(),
                        selectedAttributes = currentAppliedFilterMethod
                            ?.selectedAttributes
                            ?.values
                            ?.flatten()
                            ?: emptyList(),
                        onFilterApplyButtonClicked = {
                            scope.launch {
                                bottomSheetState.hide()
                                categoryViewModel.changeBottomSheetState(CategoryViewModel.CategoryScreenBottomSheetState.COLLAPSED)
                                categoryViewModel.fetchSortedProductsByCategoryId(categoryId = id, filterModel = it)
                            }
                        },
                        onDismissButtonClicked = {
                            scope.launch {
                                bottomSheetState.hide()
                                categoryViewModel.changeBottomSheetState(CategoryViewModel.CategoryScreenBottomSheetState.COLLAPSED)
                            }
                        },
                    )
                }
            }
            CategoryViewModel.CategoryScreenBottomSheetState.CART -> {
                ModalBottomSheet(
                    onDismissRequest = { categoryViewModel.changeBottomSheetState(CategoryViewModel.CategoryScreenBottomSheetState.COLLAPSED) },
                    sheetState = bottomSheetState,
                ) {
                    CartBottomSheetContent {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                categoryViewModel.changeBottomSheetState(CategoryViewModel.CategoryScreenBottomSheetState.COLLAPSED)
                                showOrderFormDialog = true
                            }
                        }
                    }
                }
            }
        }
    }
}