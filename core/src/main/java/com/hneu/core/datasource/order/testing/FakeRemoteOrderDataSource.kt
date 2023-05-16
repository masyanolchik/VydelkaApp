package com.hneu.core.datasource.order.testing

import com.hneu.core.datasource.order.RemoteOrderDataSource
import com.hneu.core.datasource.product.testing.FakeRemoteProductDataSource
import com.hneu.core.domain.order.Order
import com.hneu.core.domain.order.OrderedProduct
import com.hneu.core.domain.request.Result
import com.hneu.core.domain.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRemoteOrderDataSource : RemoteOrderDataSource {
    override fun saveOrder(order: Order, user: User): Flow<Result<Order>> {
        TODO("Not yet implemented")
    }

    override fun getOrders(user: User): Flow<Result<List<Order>>> {
        return flowOf(Result.Success(FAKE_ORDERS))
    }

    companion object {
        private val FAKE_USER = User (
            id = 0,
            name = "TikTak",
            lastname = "Robina",
            phoneNumber = "0988537640",
            address = "Evegreen terace 742",
            orderHistory = emptyList(),
            productHistory = emptyList(),
            favoriteProducts = emptyList(),
        )
        private val FAKE_ORDERED_PRODUCTS_1 = listOf(
            OrderedProduct(FakeRemoteProductDataSource.FAKE_LIST[0], 1),
            OrderedProduct(FakeRemoteProductDataSource.FAKE_LIST[1], 1),
        )
        private val FAKE_ORDERED_PRODUCTS_2 = listOf(
            OrderedProduct(FakeRemoteProductDataSource.FAKE_LIST[1], 3),
            OrderedProduct(FakeRemoteProductDataSource.FAKE_LIST[2], 2),
        )
        val FAKE_ORDERS = mutableListOf(
            Order(
                id = 0,
                dateOfOrder = "09:10 2022.03.04",
                optionalRegisteredCustomer = FAKE_USER,
                orderStatus = "В процесі",
                orderedProducts = FAKE_ORDERED_PRODUCTS_1,
            ),
            Order(
                id = 1,
                dateOfOrder = "21:10 2022.04.21",
                optionalRegisteredCustomer = FAKE_USER,
                orderStatus = "Закінчено",
                orderedProducts = FAKE_ORDERED_PRODUCTS_2,
            ),
        )
    }
}