package com.hneu.vydelka.localdatabase.user

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hneu.vydelka.localdatabase.order.LocalOrder
import com.hneu.vydelka.localdatabase.order.LocalOrderWithAdditionalFields
import com.hneu.vydelka.localdatabase.product.LocalProduct
import com.hneu.vydelka.localdatabase.product.LocalProductWithAdditionalFields

data class LocalUserWithAdditionalFields(
    @Embedded val localUser: LocalUser,
    @Relation(
        entity = LocalProduct::class,
        parentColumn = "userId",
        entityColumn = "productId",
        associateBy = Junction(UserProductHistoryCrossRef::class)
    )
    val userProductHistory: List<LocalProductWithAdditionalFields>,
    @Relation(
        entity = LocalProduct::class,
        parentColumn = "userId",
        entityColumn = "productId",
        associateBy = Junction(UserProductHistoryCrossRef::class)
    )
    val userProductFavorites: List<LocalProductWithAdditionalFields>,
    @Relation(
        entity = LocalOrder::class,
        parentColumn = "userId",
        entityColumn = "orderId",
        associateBy = Junction(UserOrderHistoryCrossRef::class)
    )
    val userOrderHistory: List<LocalOrderWithAdditionalFields>,
)
