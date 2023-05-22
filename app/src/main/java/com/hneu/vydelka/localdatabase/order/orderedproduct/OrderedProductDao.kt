package com.hneu.vydelka.localdatabase.order.orderedproduct

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hneu.core.domain.order.OrderedProduct
import com.hneu.vydelka.localdatabase.order.LocalOrder

@Dao
interface OrderedProductDao {
    @Insert(onConflict = REPLACE)
    fun addOrderedProduct(localOrderedProduct: LocalOrderedProduct)

    @Update(onConflict = REPLACE)
    fun updateOrderedProduct(localOrderedProduct: LocalOrderedProduct)

    @Delete
    fun deleteOrderedProduct(orderedProduct: LocalOrderedProduct)

    @Transaction
    @Query("SELECT * from localorderedproduct WHERE orderedProductId=:orderedProductId")
    fun getOrderedProduct(orderedProductId: Int): OrderedProductWithAdditionalFields
}