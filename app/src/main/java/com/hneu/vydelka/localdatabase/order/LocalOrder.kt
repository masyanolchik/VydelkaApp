package com.hneu.vydelka.localdatabase.order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hneu.core.domain.order.Order
import com.hneu.core.domain.user.User
import com.hneu.vydelka.localdatabase.order.cart.toDomain

@Entity(tableName="orders")
data class LocalOrder(
    @PrimaryKey val orderId: Int,
    @ColumnInfo(name = "date_of_order") val dateOfOrder: String,
    @ColumnInfo(name = "non_registered_customer_name") val nonRegisteredCustomerName: String,
    @ColumnInfo(name = "non_registered_customer_lastname") val nonRegisteredCustomerLastName: String,
    @ColumnInfo(name = "non_registered_customer_phone_number") val nonRegisteredCustomerPhoneNumber: String,
    @ColumnInfo(name = "non_registered_customer_address") val nonRegisteredCustomerAddress: String,
    @ColumnInfo(name = "order_status") val orderStatus: String,
    @ColumnInfo(name = "cart_id") val cartId: Int,
)

fun LocalOrderWithAdditionalFields.toDomain() =
    Order(
        id = localOrder.orderId,
        dateOfOrder = localOrder.dateOfOrder,
        nonRegisteredCustomerName = localOrder.nonRegisteredCustomerName,
        nonRegisteredCustomerLastname = localOrder.nonRegisteredCustomerLastName,
        nonRegisteredCustomerPhone = localOrder.nonRegisteredCustomerPhoneNumber,
        nonRegisteredCustomerAddress = localOrder.nonRegisteredCustomerAddress,
        orderStatus = localOrder.orderStatus,
        cart = localCart.toDomain()
    )

fun Order.fromDomain() =
    LocalOrder(
        orderId = id,
        dateOfOrder = dateOfOrder,
        nonRegisteredCustomerName = nonRegisteredCustomerName,
        nonRegisteredCustomerLastName = nonRegisteredCustomerLastname,
        nonRegisteredCustomerPhoneNumber = nonRegisteredCustomerPhone,
        nonRegisteredCustomerAddress = nonRegisteredCustomerAddress,
        orderStatus = orderStatus,
        cartId = cart.id,
    )