package com.hneu.vydelka.localdatabase.order

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(localOrder: LocalOrder): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateOrder(localOrder: LocalOrder)

    @Delete
    fun deleteOrder(localOrder: LocalOrder)

    @Transaction
    @Query("SELECT * from orders WHERE cart_id IN (SELECT c.cartId from carts c WHERE c.userId=:userId)")
    fun getUserOrders(userId: Int): List<LocalOrderWithAdditionalFields>
}