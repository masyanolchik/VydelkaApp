package com.hneu.vydelka.localdatabase.order.orderedproduct

import androidx.room.Embedded
import androidx.room.Relation
import com.hneu.vydelka.localdatabase.order.cart.LocalCart
import com.hneu.vydelka.localdatabase.order.cart.LocalCartWithAdditionalFields
import com.hneu.vydelka.localdatabase.product.LocalProduct
import com.hneu.vydelka.localdatabase.product.LocalProductWithAdditionalFields

data class OrderedProductWithAdditionalFields(
    @Embedded val localOrderedProduct: LocalOrderedProduct,
    @Relation(
        entity = LocalProduct::class,
        parentColumn = "productId",
        entityColumn = "productId",
    )
    val localProduct: LocalProductWithAdditionalFields,
)
