package com.hneu.core.usecase.order

import com.hneu.core.domain.user.User
import com.hneu.core.repository.order.OrderRepository

class FetchOrdersUseCase(private val orderRepository: OrderRepository) {
    operator fun invoke(user: User) = orderRepository.fetchOrders(user)
}