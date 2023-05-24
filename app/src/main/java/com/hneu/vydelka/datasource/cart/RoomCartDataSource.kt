package com.hneu.vydelka.datasource.cart

import com.hneu.core.datasource.cart.LocalCartDataSource
import com.hneu.core.domain.order.Cart
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.localdatabase.order.cart.CartDao
import com.hneu.vydelka.localdatabase.order.cart.CartOrderedProductsCrossRef
import com.hneu.vydelka.localdatabase.order.cart.fromDomain
import com.hneu.vydelka.localdatabase.order.cart.toDomain
import com.hneu.vydelka.localdatabase.order.orderedproduct.OrderedProductDao
import com.hneu.vydelka.localdatabase.order.orderedproduct.fromDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RoomCartDataSource @Inject constructor(
    private val cartDao: CartDao,
    private val orderedProductDao: OrderedProductDao,
) : LocalCartDataSource {
    override fun saveCart(cart: Cart): Flow<Result<Cart>> {
        return try {
            cartDao.addCart(cart.fromDomain())
            cart.orderedProducts.forEach {
                orderedProductDao.addOrderedProduct(it.fromDomain())
                cartDao.addOrderedProductCrossRef(CartOrderedProductsCrossRef(
                    cartId = cart.id,
                    orderedProductId = it.fromDomain().orderedProductId),
                )
            }
            getCart(cart.optionalUserId?:0)
        } catch(e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    override fun getCart(userId: Int): Flow<Result<Cart>> {
        return try {
            flowOf(Result.Success(cartDao.getUserCart(userId).toDomain()))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }
}