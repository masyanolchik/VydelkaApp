package com.hneu.vydelka.localdatabase.product.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.hneu.core.domain.product.AttributeGroup
import com.hneu.core.domain.product.Category
import com.hneu.vydelka.localdatabase.product.attributegroup.toDomain

@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(
            entity = LocalCategory::class,
            parentColumns = ["categoryId"],
            childColumns = ["parent_category_id"],
        )
    ]
)
data class LocalCategory(
    @PrimaryKey val categoryId: Int,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name = "parent_category_id") val parentCategoryId: Int? = null,
)

fun LocalCategory.toDomain(parentCategory: Category?, allowedAttributeGroupValues: List<AttributeGroup>) =
    Category(
        name,
        parentCategory,
        allowedAttributeGroupValues,
        categoryId,
    )

fun LocalCategoryWithLocalAttributeGroups.toDomain(parentCategory: Category?) =
    Category(
        localCategory.name,
        parentCategory,
        allowedAttributeValues.map { it.toDomain() },
        localCategory.categoryId,
    )

fun Category.fromDomain() =
    Pair(LocalCategory(id, name, parentCategory?.id), attributeGroups.map { CategoryAttributeGroupsCrossRef(id, it.id) })
