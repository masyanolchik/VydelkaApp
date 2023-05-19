package com.hneu.vydelka.localdatabase.product

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.hneu.vydelka.localdatabase.product.additionalimage.LocalAdditionalImage

@Entity(
    primaryKeys = ["productId", "additionalImageId"],
    indices = [Index("productId"), Index("additionalImageId")],
    foreignKeys = [ForeignKey(
        parentColumns = ["productId"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE,
        entity = LocalProduct::class,
    ), ForeignKey(
        parentColumns = ["additionalImageId"],
        childColumns = ["additionalImageId"],
        onDelete = ForeignKey.CASCADE,
        entity = LocalAdditionalImage::class
    )],
)
data class ProductAdditionalImagesCrossRef(
    val productId: Int,
    val additionalImageId: Int,
)