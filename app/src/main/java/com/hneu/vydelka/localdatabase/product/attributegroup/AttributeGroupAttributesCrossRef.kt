package com.hneu.vydelka.localdatabase.product.attributegroup

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.hneu.vydelka.localdatabase.product.attribute.LocalAttribute

@Entity(
    primaryKeys = ["attributeGroupId", "attributeId"],
    indices = [Index("attributeGroupId"), Index("attributeId")],
    foreignKeys = [ForeignKey(
        parentColumns = ["attributeGroupId"],
        childColumns = ["attributeGroupId"],
        onDelete = ForeignKey.CASCADE,
        entity = LocalAttributeGroup::class,
    ), ForeignKey(
        parentColumns = ["attributeId"],
        childColumns = ["attributeId"],
        onDelete = ForeignKey.CASCADE,
        entity = LocalAttribute::class
    )],
)
data class AttributeGroupAttributesCrossRef(
    val attributeGroupId: Int,
    val attributeId: Int,
)