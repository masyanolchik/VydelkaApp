package com.hneu.vydelka.localdatabase.product.category

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.hneu.vydelka.localdatabase.product.attributegroup.LocalAttributeGroup

@Entity(
    primaryKeys = ["categoryId", "attributeGroupId"],
    indices = [Index("categoryId"), Index("attributeGroupId")],
    foreignKeys = [ForeignKey(
        parentColumns = ["categoryId"],
        childColumns = ["categoryId"],
        entity = LocalCategory::class,
    ), ForeignKey(
        parentColumns = ["attributeGroupId"],
        childColumns = ["attributeGroupId"],
        entity = LocalAttributeGroup::class
    )],
)
data class CategoryAttributeGroupsCrossRef(
    val categoryId: Int,
    val attributeGroupId: Int,
)