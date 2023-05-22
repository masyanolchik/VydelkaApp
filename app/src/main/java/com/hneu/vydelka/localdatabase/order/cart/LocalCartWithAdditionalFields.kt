package com.hneu.vydelka.localdatabase.order.cart

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hneu.vydelka.localdatabase.order.orderedproduct.LocalOrderedProduct
import com.hneu.vydelka.localdatabase.order.orderedproduct.OrderedProductWithAdditionalFields
import com.hneu.vydelka.localdatabase.user.LocalUser
import com.hneu.vydelka.localdatabase.user.LocalUserWithAdditionalFields

data class LocalCartWithAdditionalFields(
    @Embedded val localCart: LocalCart,
    @Relation(
        entity = LocalOrderedProduct::class,
        parentColumn = "cartId",
        entityColumn = "orderedProductId",
        associateBy = Junction(CartOrderedProductsCrossRef::class),
    )
    val orderedProductsList: List<OrderedProductWithAdditionalFields>
)
