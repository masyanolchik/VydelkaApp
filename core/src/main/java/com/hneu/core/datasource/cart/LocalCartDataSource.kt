package com.hneu.core.datasource.cart

import com.hneu.core.domain.order.Cart
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface LocalCartDataSource {
    fun saveCart(cart: Cart): Flow<Result<Cart>>
    fun getCart(): Flow<Result<Cart>>
}