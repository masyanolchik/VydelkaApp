package com.hneu.core.domain.promo

import com.hneu.core.domain.product.Tag

data class Promo(
    val id: Int,
    val name: String,
    val titleImageSrc: String,
    val detailedDescription: String,
    val tags: List<Tag>,
)