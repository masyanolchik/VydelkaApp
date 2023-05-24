package com.hneu.vydelka.localdatabase.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun addUser(localUser: LocalUser)

    @Insert(onConflict = REPLACE)
    fun addUserOrderHistoryCrossRef(userOrderHistoryCrossRef: UserOrderHistoryCrossRef)

    @Delete
    fun deleteUserOrderHistoryCrossRef(userOrderHistoryCrossRef: UserOrderHistoryCrossRef)

    @Insert(onConflict = REPLACE)
    fun addProductFavoriteCrossRef(productFavoriteCrossRef: UserProductFavoriteCrossRef)

    @Delete
    fun deleteProductFavoriteCrossRef(productFavoriteCrossRef: UserProductFavoriteCrossRef)

    @Insert(onConflict = REPLACE)
    fun addProductHistoryCrossRef(productHistoryCrossRef: UserProductHistoryCrossRef)

    @Delete
    fun deleteProductHistoryCrossRef(productHistoryCrossRef: UserProductHistoryCrossRef)

    @Update(onConflict = REPLACE)
    fun updateUser(localUser: LocalUser)

    @Delete
    fun deleteUser(localUser: LocalUser)

    @Query("SELECT EXISTS (SELECT * FROM users WHERE username=:userName)")
    fun isUserExists(userName: String): Boolean

    @Query("SELECT * from users WHERE username=:userName")
    fun getUserByUsername(userName: String): LocalUserWithAdditionalFields

    @Query("SELECT EXISTS (SELECT * FROM users WHERE userId=:userId)")
    fun isUserExists(userId: Int): Boolean

    @Query("SELECT * from users WHERE userId=:userId")
    fun getUserById(userId: Int): LocalUser

    @Transaction
    @Query("SELECT * from users WHERE userId=:userId")
    fun getUserWithFieldsById(userId: Int): LocalUserWithAdditionalFields
}