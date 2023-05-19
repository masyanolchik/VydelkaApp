package com.hneu.core.repository.order

import com.hneu.core.datasource.order.LocalOrderDataSource
import com.hneu.core.datasource.order.RemoteOrderDataSource
import com.hneu.core.domain.order.Order
import com.hneu.core.domain.request.Result
import com.hneu.core.domain.user.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalCoroutinesApi::class)
class CoreOrderRepository(
    private val localDataSource: LocalOrderDataSource,
    private val remoteDataSource: RemoteOrderDataSource,
) : OrderRepository {
    override fun saveOrder(order: Order, user: User?): Flow<Result<Order>> {
        return remoteDataSource
            .saveOrder(order, user)
            .flatMapLatest { result ->
                when(result) {
                    is Result.Success -> localDataSource.saveOrder(order, user)
                    else -> flowOf(result)
                }
            }
    }

    override fun fetchOrders(user: User): Flow<Result<List<Order>>> {
        return remoteDataSource
            .getOrders(user)
            .flatMapLatest { result ->
                when(result) {
                    is Result.Success -> localDataSource.saveOrders(result.data, user)
                    else -> flowOf(result)
                }
            }
    }
}