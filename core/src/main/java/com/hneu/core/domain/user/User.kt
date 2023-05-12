package com.hneu.core.domain.user

import com.hneu.core.domain.order.Order
import com.hneu.core.domain.product.Product

data class User(
    val id: Int,
    val name: String,
    val lastname: String,
    val phoneNumber: String,
    val address: String,
    val orderHistory: List<Order>,
    val productHistory: List<Product>,
    val favoriteProducts: List<Product>,
)
