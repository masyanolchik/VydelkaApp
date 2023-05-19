package com.hneu.vydelka.localdatabase.product.attributegroup

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.hneu.core.domain.product.Attribute
import com.hneu.core.domain.product.AttributeGroup
import com.hneu.vydelka.localdatabase.product.attribute.toDomain
import com.hneu.vydelka.localdatabase.product.category.LocalCategory

@Entity(
    tableName="attributeGroups",
)
data class LocalAttributeGroup(
    @PrimaryKey val attributeGroupId: Int,
    @ColumnInfo(name = "name") val name: String,
)

fun LocalAttributeGroup.toDomain(allowedAttributes: List<Attribute>) =
    AttributeGroup(
        attributeGroupId,
        name,
        allowedAttributes
    )

fun LocalAttributeGroupWithAllowedValues.toDomain() =
    AttributeGroup(
        localAttributeGroup.attributeGroupId,
        localAttributeGroup.name,
        allowedAttributeValues.map { it.toDomain() }
    )

fun AttributeGroup.fromDomain() =
    Pair(
        LocalAttributeGroup(id, name),
        attributes.map { AttributeGroupAttributesCrossRef(id,it.id)}
    )