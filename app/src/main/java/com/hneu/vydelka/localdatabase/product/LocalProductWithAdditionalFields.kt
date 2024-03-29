package com.hneu.vydelka.localdatabase.product

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hneu.vydelka.localdatabase.product.additionalimage.LocalAdditionalImage
import com.hneu.vydelka.localdatabase.product.attribute.LocalAttribute
import com.hneu.vydelka.localdatabase.product.category.LocalCategory
import com.hneu.vydelka.localdatabase.product.category.LocalCategoryWithLocalAttributeGroups
import com.hneu.vydelka.localdatabase.product.tag.LocalTag

data class LocalProductWithAdditionalFields(
    @Embedded val localProduct: LocalProduct,
    @Relation(
        entity = LocalCategory::class,
        parentColumn = "productId",
        entityColumn = "categoryId"
    )
    var localCategory: LocalCategoryWithLocalAttributeGroups?,
    @Relation(
        parentColumn = "productId",
        entityColumn = "tagId",
        associateBy = Junction(ProductTagsCrossRef::class)
    )
    val tagList: List<LocalTag>,
    @Relation(
        parentColumn = "productId",
        entityColumn = "attributeId",
        associateBy = Junction(ProductAttributesCrossRef::class)
    )
    val attributeList: List<LocalAttribute>,
    @Relation(
        parentColumn = "productId",
        entityColumn = "additionalImageId",
        associateBy = Junction(ProductAdditionalImagesCrossRef::class)
    )
    val additionalImages: List<LocalAdditionalImage>
)
