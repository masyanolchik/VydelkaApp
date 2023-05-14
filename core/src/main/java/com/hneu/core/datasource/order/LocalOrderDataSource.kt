package com.hneu.core.datasource.order

import com.hneu.core.domain.order.Order
import com.hneu.core.domain.user.User
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface LocalOrderDataSource {
    fun saveOrder(order: Order): Flow<Result>
    fun getOrders(user: User): Flow<Result>
}