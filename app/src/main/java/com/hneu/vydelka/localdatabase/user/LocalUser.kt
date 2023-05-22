package com.hneu.vydelka.localdatabase.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hneu.core.domain.order.Order
import com.hneu.core.domain.user.User
import com.hneu.vydelka.localdatabase.order.toDomain
//import com.hneu.vydelka.localdatabase.order.toDomain
import com.hneu.vydelka.localdatabase.product.toDomain

@Entity(tableName="users")
data class LocalUser(
    @PrimaryKey val userId: Int,
    @ColumnInfo(name ="name") val name: String,
    @ColumnInfo(name = "lastname") val lastName: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "address") val address: String,
)

fun LocalUserWithAdditionalFields.toDomain() =
    User(
        id = localUser.userId,
        name = localUser.name,
        lastname = localUser.lastName,
        phoneNumber = localUser.phoneNumber,
        address = localUser.address,
        orderHistory = userOrderHistory.map { it.toDomain() },
        productHistory = userProductHistory.map { it.toDomain() },
        favoriteProducts = userProductFavorites.map { it.toDomain() }
    )

fun User.fromDomain() =
    LocalUser(
        userId = id,
        name = name,
        lastName = lastname,
        phoneNumber = phoneNumber,
        address = address,
    )

