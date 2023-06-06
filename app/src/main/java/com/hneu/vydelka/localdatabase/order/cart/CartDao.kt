package com.hneu.vydelka.localdatabase.order.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface CartDao {
    @Insert(onConflict = REPLACE)
    fun addCart(localCart: LocalCart): Long

    @Update(onConflict = REPLACE)
    fun updateCart(localCart: LocalCart)

    @Delete
    fun deleteCart(localCart: LocalCart)

    @Insert(onConflict = REPLACE)
    fun addOrderedProductCrossRef(cartOrderedProductsCrossRef: CartOrderedProductsCrossRef)

    @Query("SELECT * from cartorderedproductscrossref")
    fun getOrderedProductsCrossRef(): List<CartOrderedProductsCrossRef>

    @Delete
    fun deleteOrderedProductCrossRef(cartOrderedProductsCrossRef: CartOrderedProductsCrossRef)

    @Transaction
    @Query("SELECT * from carts WHERE userId=:userId AND cartId NOT IN (SELECT cart_id from orders)")
    fun getUserCart(userId: Int): LocalCartWithAdditionalFields
}