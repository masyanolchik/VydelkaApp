package com.hneu.vydelka.localdatabase.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hneu.core.domain.user.User
import com.hneu.vydelka.localdatabase.order.toDomain
//import com.hneu.vydelka.localdatabase.order.toDomain
import com.hneu.vydelka.localdatabase.product.toDomain

@Entity(tableName="users")
data class LocalUser(
    @PrimaryKey val userId: Int,
    @ColumnInfo(name = "username") val userName: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name ="name") val name: String,
    @ColumnInfo(name = "lastname") val lastName: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "address") val address: String,
)

fun LocalUserWithAdditionalFields.toDomain() =
    User(
        id = localUser.userId,
        username = localUser.userName,
        password = localUser.password,
        name = localUser.name,
        lastName = localUser.lastName,
        phoneNumber = localUser.phoneNumber,
        shippingAddress = localUser.address,
        orderHistory = userOrderHistory.map { it.toDomain() },
        productHistory = userProductHistory.map { it.toDomain() },
        favoriteProducts = userProductFavorites.map { it.toDomain() }
    )

fun User.fromDomain() =
    LocalUser(
        userId = id,
        userName = username,
        password = password,
        name = name,
        lastName = lastName,
        phoneNumber = phoneNumber,
        address = shippingAddress,
    )

