package com.hneu.vydelka.localdatabase.order.cart

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.hneu.core.domain.order.Cart
import com.hneu.core.domain.user.User
import com.hneu.vydelka.localdatabase.order.orderedproduct.toDomain
import com.hneu.vydelka.localdatabase.user.LocalUser
import com.hneu.vydelka.localdatabase.user.toDomain

@Entity(
    tableName = "carts",
    foreignKeys = [
        ForeignKey(
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            entity = LocalUser::class
        ),
    ]
)
data class LocalCart(
    @PrimaryKey val cartId: Int,
    val userId: Int?,
)

fun LocalCartWithAdditionalFields.toDomain() =  Cart(
        id = localCart.cartId,
        optionalUserId = localCart.userId,
        orderedProducts = orderedProductsList.map { it.toDomain() }.toMutableList()
    )


fun Cart.fromDomain() =
    LocalCart(
        cartId = id,
        userId = optionalUserId
    )
