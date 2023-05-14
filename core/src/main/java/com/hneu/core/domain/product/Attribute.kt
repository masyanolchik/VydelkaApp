package com.hneu.core.domain.product

data class Attribute(
    val id: Int,
    val name: String,
    var attributeGroup: AttributeGroup
)