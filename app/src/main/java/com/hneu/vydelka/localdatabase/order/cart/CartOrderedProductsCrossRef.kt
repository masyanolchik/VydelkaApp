package com.hneu.vydelka.localdatabase.order.cart

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.hneu.vydelka.localdatabase.order.orderedproduct.LocalOrderedProduct

@Entity(
    primaryKeys = ["cartId", "orderedProductId"],
    indices = [Index("cartId"), Index("orderedProductId")],
    foreignKeys = [
        ForeignKey(
            parentColumns = ["cartId"],
            childColumns = ["cartId"],
            onDelete = ForeignKey.CASCADE,
            entity = LocalCart::class,
        ),
        ForeignKey(
            parentColumns = ["orderedProductId"],
            childColumns = ["orderedProductId"],
            onDelete = ForeignKey.CASCADE,
            entity = LocalOrderedProduct::class
        )
    ]
)
data class CartOrderedProductsCrossRef(
    val cartId: Int,
    val orderedProductId: Int,
)
