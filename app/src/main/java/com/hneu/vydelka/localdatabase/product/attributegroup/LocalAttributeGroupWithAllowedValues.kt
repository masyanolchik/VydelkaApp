package com.hneu.vydelka.localdatabase.product.attributegroup

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hneu.vydelka.localdatabase.product.attribute.LocalAttribute

data class LocalAttributeGroupWithAllowedValues(
    @Embedded val localAttributeGroup: LocalAttributeGroup,
    @Relation(
        parentColumn = "attributeGroupId",
        entityColumn = "attributeId",
        associateBy = Junction(AttributeGroupAttributesCrossRef::class)
    )
    val allowedAttributeValues: List<LocalAttribute>
)
