package com.hneu.vydelka.localdatabase.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.hneu.vydelka.localdatabase.product.LocalProduct

@Entity(
    primaryKeys=["userId", "productId"],
    indices = [Index("userId"), Index("productId")],
    foreignKeys = [
        ForeignKey(
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            entity = LocalUser::class,
        ),
        ForeignKey(
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE,
            entity = LocalProduct::class,
        ),
    ]
)
data class UserProductHistoryCrossRef(
    val userId: Int,
    val productId: Int,
)