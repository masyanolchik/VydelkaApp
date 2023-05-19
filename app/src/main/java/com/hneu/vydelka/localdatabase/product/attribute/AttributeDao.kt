package com.hneu.vydelka.localdatabase.product.attribute

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface AttributeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAttribute(localAttribute: LocalAttribute): Long

    @Update
    fun updateAttribute(localAttribute: LocalAttribute)

    @Delete
    fun deleteAttribute(localAttribute: LocalAttribute)

    @Query("SELECT * from attributes WHERE attributeId=:attributeId")
    fun getAttributeById(attributeId: Int): LocalAttribute
}