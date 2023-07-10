package com.hneu.vydelka.network.order

import com.hneu.core.domain.order.Cart
import com.hneu.core.domain.order.Order
import com.hneu.core.domain.order.OrderedProduct
import com.hneu.core.domain.user.User
import com.hneu.vydelka.network.product.ProductResponseDataModel
import com.hneu.vydelka.network.product.toDomain

data class OrderResponseDataModel(
    val id: Int,
    val buyersName: String,
    val buyersPhone: String,
    val shippingAddress: String,
    val optionalUser: User?,
    val orderedProducts: List<OrderedProductResponseDataModel>,
    val date: String,
    val orderStatus: String,
 )

data class OrderedProductResponseDataModel(
    val id: Int,
    val productResponse: ProductResponseDataModel,
    val quantity: Int,
)

fun OrderResponseDataModel.toDomain(): Order {
    return Order(
        id = id,
        dateOfOrder = date,
        nonRegisteredCustomerName = buyersName.split(" ")[0],
        nonRegisteredCustomerLastname = buyersName.split(" ")[1],
        nonRegisteredCustomerPhone = buyersPhone,
        nonRegisteredCustomerAddress = shippingAddress,
        cart = Cart(
           optionalUserId = optionalUser?.id,
           orderedProducts = orderedProducts.map { OrderedProduct(
               id = 0,
               quantity = it.quantity,
               product = it.productResponse.toDomain()
           ) }.toMutableList()),
        orderStatus = orderStatus
    )
}