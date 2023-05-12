package com.hneu.core.datasource.order

import com.hneu.core.domain.order.Order
import com.hneu.core.domain.user.User
import kotlinx.coroutines.flow.Flow

interface LocalOrderDataSource {
    fun saveOrder(order: Order): Flow<List<Result<Order>>>
    fun getOrders(user: User): Flow<Result<List<Order>>>
}