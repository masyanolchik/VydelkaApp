package com.hneu.core.domain.order

import com.hneu.core.domain.user.User
import java.math.BigDecimal

data class Order(
    val id: Int,
    val dateOfOrder: String,
    val nonRegisteredCustomerName: String = "",
    val nonRegisteredCustomerLastname: String = "",
    val nonRegisteredCustomerPhone: String = "",
    val nonRegisteredCustomerAddress: String = "",
    val orderedProducts: List<OrderedProduct>,
    val optionalRegisteredCustomer: User?,
    val orderStatus: String,
) {
    fun getOrderSum() : BigDecimal {
      val overallSum = BigDecimal(0)
      orderedProducts.forEach {
          overallSum.add(it.product.price)
      }
      return overallSum
    }
}
