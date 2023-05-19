package com.hneu.core.domain.product

data class Category(
    val name: String,
    var parentCategory: Category? = null,
    val attributeGroups: List<AttributeGroup>,
    val id: Int = 0,
)