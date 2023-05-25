package com.hneu.vydelka.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.promo.Promo
import com.hneu.core.repository.promo.PromoRepository
import com.hneu.core.domain.request.Result
import com.hneu.core.usecase.product.FetchProductsUseCase
import com.hneu.core.usecase.product.GetProductsByCategoryIdUseCase
import com.hneu.core.usecase.product.GetTopProductsUseCase
import com.hneu.vydelka.accountmanager.AccountManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val promoRepository: PromoRepository,
    private val getTopProductsUseCase: GetTopProductsUseCase,
    private val getProductsByCategoryIdUseCase: GetProductsByCategoryIdUseCase,
    private val accountManager: AccountManager,
) : ViewModel() {
    private var _promosStateFlow : MutableStateFlow<Result<List<Promo>>> =
        MutableStateFlow(Result.Loading())
    val promosStateFlow = _promosStateFlow

    private var _topProducts: MutableStateFlow<Result<List<Product>>> =
        MutableStateFlow(Result.Loading())
    val topProducts = _topProducts

    val isUserLoggedIn = accountManager.isUserLoggedIn()

    var categoryToProductsMap = MutableStateFlow(mutableMapOf<Int, Result<List<Product>>>())


    init {
        viewModelScope.launch {
            promoRepository
                .fetchPromos()
                .flowOn(Dispatchers.IO)
                .distinctUntilChanged()
                .collectLatest {
                    _promosStateFlow.emit(it)
                }
            getTopProductsUseCase
                .invoke()
                .flowOn(Dispatchers.IO)
                .distinctUntilChanged()
                .collectLatest {
                    _topProducts.emit(it)
                }

        }
    }

    fun fetchProductsByCategoryId(categoryId: Int): StateFlow<Result<List<Product>>> {
        val stateFlow: MutableStateFlow<Result<List<Product>>> = MutableStateFlow(Result.Loading())
        CoroutineScope(Dispatchers.IO).launch {
            getProductsByCategoryIdUseCase
                .invoke(categoryId)
                .distinctUntilChanged()
                .collectLatest {
                    categoryToProductsMap.value[categoryId] = it
                }
        }
        return stateFlow
    }

    fun addProductToCart(product: Product) {
        accountManager.addProductToCart(product)
    }

    fun addProductToFavorites(product: Product) {
        accountManager.addProductToFavorites(product)
    }

    fun removeProductFromCart(product: Product) {
        accountManager.removeProductFromCart(product)
    }

    fun removeProductFromFavorites(product: Product) {
        accountManager.removeProductFromFavorites(product)
    }
}