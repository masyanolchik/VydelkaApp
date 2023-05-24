package com.hneu.vydelka.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.promo.Promo
import com.hneu.core.repository.promo.PromoRepository
import com.hneu.core.domain.request.Result
import com.hneu.core.usecase.product.GetTopProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val promoRepository: PromoRepository,
    private val getTopProductsUseCase: GetTopProductsUseCase,
) : ViewModel() {
    private var _promosStateFlow : MutableStateFlow<Result<List<Promo>>> =
        MutableStateFlow(Result.Loading())
    val promosStateFlow = _promosStateFlow

    private var _topProducts: MutableStateFlow<Result<List<Product>>> =
        MutableStateFlow(Result.Loading())
    val topProducts = _topProducts

    init {
        fetchPromos()
        fetchTopProducts()
    }

    fun fetchPromos() {
        viewModelScope.launch {
            promoRepository
                .fetchPromos()
                .flowOn(Dispatchers.IO)
                .distinctUntilChanged()
                .collectLatest {
                    _promosStateFlow.emit(it)
                }
        }
    }

    fun fetchTopProducts() {
        viewModelScope.launch {
            getTopProductsUseCase
                .invoke()
                .flowOn(Dispatchers.IO)
                .distinctUntilChanged()
                .collectLatest {
                    _topProducts.emit(it)
                }
        }
    }
}