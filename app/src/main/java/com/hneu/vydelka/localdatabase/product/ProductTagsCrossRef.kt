package com.hneu.vydelka.localdatabase.product

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.hneu.vydelka.localdatabase.product.tag.LocalTag

@Entity(
    primaryKeys = ["productId", "tagId"],
    indices = [Index("productId"), Index("tagId")],
    foreignKeys = [ForeignKey(
        parentColumns = ["productId"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE,
        entity = LocalProduct::class,
    ), ForeignKey(
        parentColumns = ["tagId"],
        childColumns = ["tagId"],
        onDelete = ForeignKey.CASCADE,
        entity = LocalTag::class
    )],
)
data class ProductTagsCrossRef(
    val productId: Int,
    val tagId: Int,
)