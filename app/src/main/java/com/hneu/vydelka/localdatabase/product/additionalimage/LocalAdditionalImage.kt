package com.hneu.vydelka.localdatabase.product.additionalimage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "additional_images")
data class LocalAdditionalImage (
    @PrimaryKey(autoGenerate = true) val additionalImageId: Int = 0,
    val additionalImageSrc: String,
)

fun LocalAdditionalImage.toDomain() = additionalImageSrc

fun String.fromDomainToLocalAdditionalImage() = LocalAdditionalImage(additionalImageSrc = this)