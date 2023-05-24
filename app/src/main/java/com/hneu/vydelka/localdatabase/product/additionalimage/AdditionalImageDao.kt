package com.hneu.vydelka.localdatabase.product.additionalimage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AdditionalImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAdditionalImage(localAdditionalImage: LocalAdditionalImage): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAdditionalImage(localAdditionalImage: LocalAdditionalImage)

    @Delete
    fun deleteAdditionalImage(localAdditionalImage: LocalAdditionalImage)

    @Query("SELECT * from additional_images")
    fun getAllAdditionalImages(): List<LocalAdditionalImage>
}