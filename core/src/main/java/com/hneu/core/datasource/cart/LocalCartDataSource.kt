package com.hneu.core.datasource.cart

import com.hneu.core.domain.order.Cart
import kotlinx.coroutines.flow.Flow

interface LocalCartDataSource {
    fun saveCart(cart: Cart): Flow<Result<Cart>>
}