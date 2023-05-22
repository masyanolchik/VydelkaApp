package com.hneu.vydelka.localdatabase.order

import androidx.room.Embedded
import androidx.room.Relation
import com.hneu.vydelka.localdatabase.order.cart.LocalCart
import com.hneu.vydelka.localdatabase.order.cart.LocalCartWithAdditionalFields

data class LocalOrderWithAdditionalFields(
    @Embedded val localOrder: LocalOrder,
    @Relation(
        entity = LocalCart::class,
        parentColumn = "orderId",
        entityColumn = "cartId",
    )
    val localCart: LocalCartWithAdditionalFields,
)
