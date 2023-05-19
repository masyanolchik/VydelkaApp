package com.hneu.core.usecase.order

import com.hneu.core.domain.order.Order
import com.hneu.core.domain.user.User
import com.hneu.core.repository.order.OrderRepository

class SaveOrderUseCase(private val orderRepository: OrderRepository) {
    operator fun invoke(order: Order, user: User?) = orderRepository.saveOrder(order, user)
}