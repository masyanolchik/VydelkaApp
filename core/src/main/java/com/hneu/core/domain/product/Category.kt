package com.hneu.core.domain.product

data class Category(
    val name: String,
    val parentCategory: Category?,
    val attributeGroup: List<AttributeGroup>,
    val id: Int = 0,
)