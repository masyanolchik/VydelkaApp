package com.hneu.vydelka.accountmanager

import com.hneu.core.domain.order.Cart
import com.hneu.core.domain.order.Order
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.user.User
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface AccountManager {
    fun getCurrentUser(): User
    fun addProductToFavorites(product: Product): Flow<Result<Product>>
    fun getCart(): Flow<Result<Cart>>
    fun getFavorites(): Flow<Result<List<Product>>>
    fun addProductToCart(product: Product): Flow<Result<Cart>>
    fun changeProductQuantityInCart(product: Product, desiredQuantity: Int): Flow<Result<Cart>>
    fun removeProductFromCart(product: Product): Flow<Result<Cart>>
    fun getOrders(): Flow<Result<List<Order>>>
    fun saveOrder(order: Order): Flow<Result<Order>>
}