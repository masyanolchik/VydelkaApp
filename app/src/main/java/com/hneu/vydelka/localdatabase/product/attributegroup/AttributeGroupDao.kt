package com.hneu.vydelka.localdatabase.product.attributegroup

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hneu.vydelka.localdatabase.product.attribute.LocalAttribute

@Dao
interface AttributeGroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAttributeGroup(localAttributeGroup: LocalAttributeGroup): Long

    @Update
    fun updateAttributeGroup(localAttributeGroup: LocalAttributeGroup)

    @Delete
    fun deleteAttribute(localAttribute: LocalAttribute)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAttributeGroupAttributesCrossRef(attributeGroupAttributesCrossRef: AttributeGroupAttributesCrossRef)

    @Query("SELECT * from attributeGroups WHERE attributeGroupId=:attributeGroupId")
    fun getAttributeGroupById(attributeGroupId: Int): LocalAttributeGroup

    @Transaction
    @Query("SELECT * from attributeGroups")
    fun getAttributeGroupsWithAllowedValues(): List<LocalAttributeGroupWithAllowedValues>

    @Transaction
    @Query("SELECT * from attributeGroups where attributeGroupId=:attributeGroupId")
    fun getAttributeGroupWithAllowedValues(attributeGroupId: Int): LocalAttributeGroupWithAllowedValues
}