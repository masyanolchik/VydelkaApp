package com.hneu.vydelka.accountmanager

import com.hneu.core.domain.order.Cart
import com.hneu.core.domain.order.Order
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.user.User
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.flow.StateFlow

interface AccountManager {
    fun isUserLoggedIn(): Flow<Boolean>
    fun getCurrentUser(): User
    fun addProductToFavorites(product: Product): StateFlow<Result<List<Product>>>
    fun removeProductFromFavorites(product: Product): StateFlow<Result<List<Product>>>
    fun getCart(): StateFlow<Cart>
    fun getFavorites(): StateFlow<Result<List<Product>>>
    fun addProductToCart(product: Product): StateFlow<Cart>
    fun changeProductQuantityInCart(product: Product, desiredQuantity: Int): StateFlow<Cart>
    fun removeProductFromCart(product: Product): StateFlow<Cart>
    fun getOrders(): Flow<Result<List<Order>>>
    fun saveOrder(order: Order): Flow<Result<Order>>
}