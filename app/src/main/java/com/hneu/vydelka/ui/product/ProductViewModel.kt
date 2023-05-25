package com.hneu.vydelka.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hneu.core.domain.product.Product
import com.hneu.core.usecase.product.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@HiltViewModel
class ProductViewModel @Inject constructor(private val getProductByIdUseCase: GetProductByIdUseCase) : ViewModel() {

    private val _productStateFlow = MutableStateFlow<Result<Product>>(Result.Loading())
    val productStateFlow = _productStateFlow

    fun getProductById(productId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            getProductByIdUseCase.invoke(productId)
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    _productStateFlow.emit(it)
                }
        }
    }

}