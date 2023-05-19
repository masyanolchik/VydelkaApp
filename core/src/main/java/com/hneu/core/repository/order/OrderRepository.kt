package com.hneu.core.repository.order

import com.hneu.core.domain.order.Order
import com.hneu.core.domain.user.User
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface OrderRepository {
    fun saveOrder(order: Order, user: User?): Flow<Result<Order>>
    fun fetchOrders(user: User): Flow<Result<List<Order>>>
}