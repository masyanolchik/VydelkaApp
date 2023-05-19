package com.hneu.vydelka.localdatabase.product

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.hneu.vydelka.localdatabase.product.attribute.LocalAttribute
import com.hneu.vydelka.localdatabase.product.tag.LocalTag

@Entity(
    primaryKeys = ["productId", "attributeId"],
    indices = [Index("productId"), Index("attributeId")],
    foreignKeys = [ForeignKey(
        parentColumns = ["productId"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE,
        entity = LocalProduct::class,
    ), ForeignKey(
        parentColumns = ["attributeId"],
        childColumns = ["attributeId"],
        onDelete = ForeignKey.CASCADE,
        entity = LocalAttribute::class
    )],
)
data class ProductAttributesCrossRef(
    val productId: Int,
    val attributeId: Int,
)