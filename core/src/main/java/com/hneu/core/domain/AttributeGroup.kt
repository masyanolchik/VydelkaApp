package com.hneu.core.domain

data class AttributeGroup(
    val name: String,
    val attributes: List<Attribute>,
    val relatedCategory: Category,
)