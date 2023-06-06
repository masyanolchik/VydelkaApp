package com.hneu.core.domain.product

import com.hneu.core.domain.product.Attribute
import java.math.BigDecimal

data class FilterModel(
    val priceRangeStart: BigDecimal,
    val priceRangeEnd: BigDecimal,
    val selectedAttributes: Map<AttributeGroup, Set<Attribute>>,
)
