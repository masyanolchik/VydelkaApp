package com.hneu.vydelka.ui.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hneu.core.domain.order.Order
import com.hneu.core.domain.product.Product
import com.hneu.vydelka.accountmanager.AccountManager
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.hneu.core.domain.request.Result
import com.hneu.core.usecase.order.SaveOrderUseCase
import com.hneu.vydelka.R
import com.hneu.vydelka.VydelkaApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

@HiltViewModel
class OrderViewModel @Inject constructor(
    application: VydelkaApplication,
    private val accountManager: AccountManager,
    private val saveOrderUseCase: SaveOrderUseCase,
) : AndroidViewModel(application) {
    val cartFlow = accountManager.getCart()

    private val _orderCreatedStatusFlow = MutableStateFlow<Result<Order>>(Result.Loading())
    val orderCreatedStatusFlow = _orderCreatedStatusFlow

    fun addProductToCart(product: Product) {
        accountManager.addProductToCart(product)
    }

    fun changeProductQuantityInCart(product: Product, desiredQuantity: Int) {
        accountManager.changeProductQuantityInCart(product, desiredQuantity)
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

    fun saveOrder(nameText: String, lastNameText: String, phoneText: String, addressText: String): Flow<Result<Order>> {
        viewModelScope.launch {
            saveOrderUseCase
                .invoke(
                    Order(
                        id = -1,
                        dateOfOrder = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time),
                        orderStatus = getApplication<VydelkaApplication>().getString(R.string.new_order_status),
                        nonRegisteredCustomerName = nameText,
                        nonRegisteredCustomerLastname = lastNameText,
                        nonRegisteredCustomerPhone = phoneText,
                        nonRegisteredCustomerAddress = addressText,
                        cart = cartFlow.value,
                    ),
                    accountManager.getCurrentUser()
                )
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    accountManager.resetCart()
                    orderCreatedStatusFlow.emit(it)
                }
        }
        return orderCreatedStatusFlow
    }
}