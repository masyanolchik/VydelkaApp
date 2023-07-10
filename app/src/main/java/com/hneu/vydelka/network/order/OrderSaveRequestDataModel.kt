package com.hneu.vydelka.network.order

import com.hneu.core.domain.order.Order
import com.hneu.core.domain.order.OrderedProduct
import com.hneu.core.domain.user.User

data class OrderSaveRequestDataModel(
    val buyersName: String,
    val buyersPhone: String,
    val shippingAddress: String,
    val userOptionalId: Int?,
    val orderedProductRequestSet: List<OrderedProductRequestDataModel>,
    val date: String,
)

data class OrderedProductRequestDataModel(
    val productId: Int,
    val quantity: Int,
)

fun Order.toNetwork(): OrderSaveRequestDataModel {
    return OrderSaveRequestDataModel(
        buyersName = "${nonRegisteredCustomerName} ${nonRegisteredCustomerLastname}",
        buyersPhone = nonRegisteredCustomerPhone,
        shippingAddress = nonRegisteredCustomerAddress,
        userOptionalId = cart.optionalUserId,
        orderedProductRequestSet = cart.orderedProducts.map { OrderedProductRequestDataModel(it.product.id,it.quantity) },
        date = dateOfOrder
    )
}
