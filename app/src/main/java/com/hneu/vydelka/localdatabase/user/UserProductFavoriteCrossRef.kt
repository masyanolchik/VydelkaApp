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
            entity = LocalUser::class,
        ),
        ForeignKey(
            parentColumns = ["productId"],
            childColumns = ["productId"],
            entity = LocalProduct::class,
        ),
    ]
)
data class UserProductFavoriteCrossRef(
    val userId: Int,
    val productId: Int,
)
