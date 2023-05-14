package com.hneu.core.repository.order

import com.hneu.core.domain.order.Order
import com.hneu.core.domain.user.User
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun saveOrder(order: Order): Flow<List<Result<Order>>>
    fun fetchOrders(user: User): Flow<Result<List<Order>>>
}