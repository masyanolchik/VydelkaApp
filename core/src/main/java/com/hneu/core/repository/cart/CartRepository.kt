package com.hneu.core.repository.cart

import com.hneu.core.domain.order.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun fetchCart(): Flow<Result<Cart>>
    fun saveCart(cart: Cart): Flow<Result<Cart>>
}