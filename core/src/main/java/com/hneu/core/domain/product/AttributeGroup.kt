package com.hneu.core.domain.product

data class AttributeGroup(
    val id: Int,
    val name: String,
    val attributes: List<Attribute>,
    val relatedCategory: Category,
)