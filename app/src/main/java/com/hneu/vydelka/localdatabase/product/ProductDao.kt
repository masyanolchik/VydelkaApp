package com.hneu.vydelka.localdatabase.product

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(localProduct: LocalProduct)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProduct(localProduct: LocalProduct)

    @Delete
    fun deleteProduct(localProduct: LocalProduct)

    @Transaction
    @Query("SELECT * from products WHERE productId=:productId")
    fun getAllProducts(productId:Int): List<LocalProductWithAdditionalFields>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductAdditionalImagesCrossRef(productAdditionalImagesCrossRef: ProductAdditionalImagesCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductAttributesCrossRef(productAttributesCrossRef: ProductAttributesCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductTagsCrossRef(productTagsCrossRef: ProductTagsCrossRef)
}