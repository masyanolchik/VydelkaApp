package com.hneu.vydelka.localdatabase.order.orderedproduct

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.hneu.core.domain.order.Order
import com.hneu.core.domain.order.OrderedProduct
import com.hneu.vydelka.localdatabase.order.LocalOrder
import com.hneu.vydelka.localdatabase.order.cart.LocalCart
import com.hneu.vydelka.localdatabase.product.LocalProduct
import com.hneu.vydelka.localdatabase.product.toDomain

@Entity(
    foreignKeys = [
        ForeignKey(
            parentColumns = ["productId"],
            childColumns = ["productId"],
            entity = LocalProduct::class,
        ),
    ]
)
data class LocalOrderedProduct(
    @PrimaryKey val orderedProductId: Int,
    val productId: Int,
    val productQuantity: Int
)

fun OrderedProductWithAdditionalFields.toDomain() =
    OrderedProduct(
        id = localOrderedProduct.orderedProductId,
        product = localProduct.toDomain(),
        quantity = localOrderedProduct.productQuantity,
    )

fun OrderedProduct.fromDomain() =
    LocalOrderedProduct(
        orderedProductId = id,
        productId = product.id,
        productQuantity = quantity,
    )