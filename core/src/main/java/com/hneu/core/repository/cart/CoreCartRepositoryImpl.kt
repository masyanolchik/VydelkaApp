package com.hneu.core.repository.cart

import com.hneu.core.datasource.cart.LocalCartDataSource
import com.hneu.core.domain.order.Cart
import com.hneu.core.repository.cart.CartRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class CoreCartRepositoryImpl(private val localDataSource: LocalCartDataSource) : CartRepository {
    override fun fetchCart(userId: Int) = localDataSource.getCart(userId)

    override fun saveCart(cart: Cart) = localDataSource.saveCart(cart)
}