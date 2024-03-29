package com.hneu.vydelka.network.product

import com.hneu.core.domain.product.Attribute
import com.hneu.core.domain.product.AttributeGroup
import com.hneu.core.domain.product.Category
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.product.Tag
import java.math.BigDecimal

data class ProductResponseDataModel (
    val id: Int,
    val name: String,
    val price: BigDecimal,
    val status: String,
    val warrantyLengthMonth: Int,
    val returnExchangeLength: Int,
    val category: Category,
    val description: String,
    val titleImageSrc: String,
    val images: List<String>,
    val attributes: List<AttributePair>,
    val additionalTags: Set<Tag> = emptySet()
)

data class AttributePair(
    val attributeGroupResponse: AttributeGroup,
    val attributeResponse: Attribute,
)

fun ProductResponseDataModel.toDomain() : Product {
    val attributeMap: MutableMap<AttributeGroup, Attribute> = mutableMapOf()
    attributes.forEach {
        attributeMap[it.attributeGroupResponse] = it.attributeResponse
    }
    return Product(
        id,
        name,
        price,
        status,
        warrantyLengthMonth,
        returnExchangeLength,
        category,
        description,
        titleImageSrc.transformToRemoteImageSrc("http://192.168.0.109:8080"),
        images.map { it.transformToRemoteImageSrc("http://192.168.0.109:8080") },
        attributeMap,
        additionalTags,
    )
}

fun String.transformToRemoteImageSrc(linkStart: String): String {
    return if(this.contains("http")) {
        this
    } else {
        "$linkStart$this"
    }
}