package com.hneu.vydelka.localdatabase.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.hneu.vydelka.localdatabase.order.LocalOrder

@Entity(
    primaryKeys=["userId", "orderId"],
    indices = [Index("userId"), Index("orderId")],
    foreignKeys = [
        ForeignKey(
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            entity = LocalUser::class,
        ),
        ForeignKey(
            parentColumns = ["orderId"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE,
            entity = LocalOrder::class,
        ),
    ]
)
data class UserOrderHistoryCrossRef(
    val userId: Int,
    val orderId: Int,
)