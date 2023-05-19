package com.hneu.vydelka.localdatabase.product.attribute

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.hneu.core.domain.product.Attribute
import com.hneu.vydelka.localdatabase.product.attributegroup.LocalAttributeGroup

@Entity(tableName = "attributes")
data class LocalAttribute(
    @PrimaryKey val attributeId: Int,
    @ColumnInfo(name="name") val name: String,
)

fun LocalAttribute.toDomain() = Attribute(attributeId, name)

fun Attribute.fromDomain() = LocalAttribute(id, name)