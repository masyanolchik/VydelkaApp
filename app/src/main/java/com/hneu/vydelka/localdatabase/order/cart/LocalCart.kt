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
            entity = LocalUser::class
        ),
    ]
)
data class LocalCart(
    val userId: Int?,
    @PrimaryKey(autoGenerate = true) val cartId: Int = 0,
)

fun LocalCartWithAdditionalFields.toDomain() =  Cart(
        id = localCart.cartId,
        optionalUserId = localCart.userId,
        orderedProducts = orderedProductsList.map { it.toDomain() }.toMutableList()
    )


fun Cart.fromDomain() =
    if(id != 0) {
        LocalCart(
            cartId = id,
            userId = optionalUserId
        )
    } else {
        LocalCart(
            userId = optionalUserId
        )
    }

