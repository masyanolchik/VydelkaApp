package com.hneu.vydelka.localdatabase.product

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.hneu.core.domain.product.Attribute
import com.hneu.core.domain.product.AttributeGroup
import com.hneu.core.domain.product.Category
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.product.Tag
import com.hneu.vydelka.localdatabase.product.additionalimage.toDomain
import com.hneu.vydelka.localdatabase.product.attribute.toDomain
import com.hneu.vydelka.localdatabase.product.category.LocalCategory
import com.hneu.vydelka.localdatabase.product.category.toDomain
import com.hneu.vydelka.localdatabase.product.tag.toDomain
import java.math.BigDecimal

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = LocalCategory::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
        )
    ]
)
data class LocalProduct (
    @PrimaryKey val productId: Int,
    val categoryId: Int,
    val name: String,
    val price: String,
    val status: String,
    val warrantyLengthMonth: Int,
    val returnExchangeLength: Int,
    val description: String,
    val titleImageSrc: String,
)

fun LocalProduct.toDomain(
    category: Category,
    additionalImages: List<String>,
    attributes: Map<AttributeGroup, Attribute>,
    additionalTags: Set<Tag> = emptySet(),
) =
    Product(
        id = productId,
        name = name,
        price = BigDecimal(price),
        status = status,
        warrantyLengthMonth = warrantyLengthMonth,
        returnExchangeLength = returnExchangeLength,
        category = category,
        description = description,
        titleImageSrc = titleImageSrc,
        images = additionalImages,
        attributes = attributes,
        additionalTags = additionalTags
    )

fun LocalProductWithAdditionalFields.toDomain(attributeGroups: List<AttributeGroup> = emptyList(), parentCategory: Category? = null) : Product {
    val category = localCategory?.toDomain(parentCategory) ?: Category("sdsd", null, emptyList())
    val attrList = attributeList.map { it.toDomain() }
    val attrMap = mutableMapOf<AttributeGroup, Attribute>()
    attrList.forEach() { attr ->
        val group = attributeGroups.find {group -> group.attributes.map { it.id }.contains(attr.id)}
        if(group != null) attrMap[group] = attr
    }
    return Product(
        id = localProduct.productId,
        name = localProduct.name,
        price = BigDecimal(localProduct.price),
        status = localProduct.status,
        warrantyLengthMonth = localProduct.warrantyLengthMonth,
        returnExchangeLength = localProduct.returnExchangeLength,
        category = category,
        description = localProduct.description,
        titleImageSrc = localProduct.titleImageSrc,
        images = additionalImages.map { it.toDomain() },
        attributes = attrMap,
        additionalTags = tagList.map{ it.toDomain() }.toSet()
    )
}

fun Product.fromDomain() =
    LocalProduct(
        productId = id,
        categoryId = category.id,
        name = name,
        price = price.toPlainString(),
        status = status,
        warrantyLengthMonth = warrantyLengthMonth,
        returnExchangeLength = returnExchangeLength,
        description = description,
        titleImageSrc = titleImageSrc,
    )


