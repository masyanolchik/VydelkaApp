package com.hneu.core.domain.product

data class Category(
    val name: String,
    var parentCategory: Category? = null,
    val attributeGroup: List<AttributeGroup>,
    val id: Int = 0,
)