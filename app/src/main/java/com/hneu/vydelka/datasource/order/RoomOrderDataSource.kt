package com.hneu.vydelka.datasource.order

import com.hneu.core.datasource.order.LocalOrderDataSource
import com.hneu.core.domain.order.Order
import com.hneu.core.domain.request.Result
import com.hneu.core.domain.user.User
import com.hneu.vydelka.localdatabase.order.OrderDao
import com.hneu.vydelka.localdatabase.order.fromDomain
import com.hneu.vydelka.localdatabase.order.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RoomOrderDataSource @Inject constructor(
    private val orderDao: OrderDao,
) : LocalOrderDataSource {
    override fun saveOrder(order: Order, user: User?): Flow<Result<Order>> {
        return try {
            val id = orderDao.insertOrder(order.fromDomain())
            flowOf(Result.Success(Order(
                id = id.toInt(),
                dateOfOrder = order.dateOfOrder,
                cart = order.cart,
                orderStatus = order.orderStatus,
                nonRegisteredCustomerAddress = order.nonRegisteredCustomerAddress,
                nonRegisteredCustomerPhone = order.nonRegisteredCustomerPhone,
                nonRegisteredCustomerLastname = order.nonRegisteredCustomerLastname,
                nonRegisteredCustomerName = order.nonRegisteredCustomerName,
            )))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    override fun saveOrders(orders: List<Order>, user: User): Flow<Result<List<Order>>> {
        return try {
            orders.forEach {
                orderDao.insertOrder(it.fromDomain())
            }
            getOrders(user)
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    override fun getOrders(user: User): Flow<Result<List<Order>>> {
        return try {
            flowOf(Result.Success(orderDao.getUserOrders(user.id).map { it.toDomain() }))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }
}