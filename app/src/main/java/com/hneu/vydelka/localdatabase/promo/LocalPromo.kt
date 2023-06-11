package com.hneu.vydelka.localdatabase.promo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hneu.core.domain.product.Tag
import com.hneu.core.domain.promo.Promo
import com.hneu.vydelka.localdatabase.product.tag.toDomain

@Entity(tableName = "promos")
data class LocalPromo(
    @PrimaryKey val promoId: Int,
    @ColumnInfo(name = "promo_name") val promoName: String,
    @ColumnInfo(name = "title_image_src") val titleImageSrc: String,
    @ColumnInfo(name = "detailed_description") val detailedDescription: String,
)

fun LocalPromo.toDomain(tagList: List<Tag>) =
    Promo(
        promoId,
        promoName,
        titleImageSrc,
        detailedDescription,
        tagList
    )

fun LocalPromoWithTags.toDomain() =
    Promo(
        localPromo.promoId,
        localPromo.promoName,
        localPromo.titleImageSrc,
        localPromo.detailedDescription,
        localTags.map { it.toDomain() }
    )

fun Promo.fromDomain() =
    LocalPromo(
        promoId = id,
        promoName = name,
        titleImageSrc = promoImageSrc,
        detailedDescription = detailedDescription
    )

fun Promo.getTagsCrossRef(existingLocalPromoId: Int) =
    tags.map { PromoTagsCrossRef(existingLocalPromoId, it.id) }