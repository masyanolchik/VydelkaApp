package com.hneu.vydelka.localdatabase.promo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.hneu.vydelka.localdatabase.product.tag.LocalTag

@Entity(
    primaryKeys = ["promoId", "tagId"],
    indices = [Index("promoId"), Index("tagId")],
    foreignKeys = [ForeignKey(
        parentColumns = ["promoId"],
        childColumns = ["promoId"],
        onDelete = ForeignKey.CASCADE,
        entity = LocalPromo::class,
    ), ForeignKey(
        parentColumns = ["tagId"],
        childColumns = ["tagId"],
        onDelete = ForeignKey.CASCADE,
        entity = LocalTag::class
    )],
)
data class PromoTagsCrossRef(
    val promoId: Int,
    val tagId: Int,
)
