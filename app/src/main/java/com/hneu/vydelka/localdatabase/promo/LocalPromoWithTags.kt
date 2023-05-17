package com.hneu.vydelka.localdatabase.promo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hneu.vydelka.localdatabase.product.tag.LocalTag

data class LocalPromoWithTags(
    @Embedded val localPromo: LocalPromo,
    @Relation(
        parentColumn = "promoId",
        entityColumn = "tagId",
        associateBy = Junction(PromoTagsCrossRef::class)
    )
    val localTags: List<LocalTag>
)
