package com.hneu.core.domain

data class Category(
    val name: String,
    val parentCategory: Category?,
    val attributeGroup: List<AttributeGroup>,

)