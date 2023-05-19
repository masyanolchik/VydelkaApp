package com.hneu.vydelka.localdatabase.product.category

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hneu.vydelka.localdatabase.product.attributegroup.LocalAttributeGroup
import com.hneu.vydelka.localdatabase.product.attributegroup.LocalAttributeGroupWithAllowedValues

data class LocalCategoryWithLocalAttributeGroups(
    @Embedded val localCategory: LocalCategory,
    @Relation(
        entity = LocalAttributeGroup::class,
        parentColumn = "categoryId",
        entityColumn = "attributeGroupId",
        associateBy = Junction(CategoryAttributeGroupsCrossRef::class)
    )
    val allowedAttributeValues: List<LocalAttributeGroupWithAllowedValues>
)
