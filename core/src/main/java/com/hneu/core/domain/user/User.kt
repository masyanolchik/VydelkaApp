package com.hneu.core.domain.user

import com.hneu.core.domain.order.Order
import com.hneu.core.domain.product.Product

data class User(
    val id: Int = -1,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val shippingAddress: String = "",
    val orderHistory: List<Order> = emptyList(),
    val productHistory: List<Product> = emptyList(),
    val favoriteProducts: List<Product> = emptyList(),
)
