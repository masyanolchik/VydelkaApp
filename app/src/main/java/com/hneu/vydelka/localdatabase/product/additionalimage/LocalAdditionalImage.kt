package com.hneu.vydelka.localdatabase.product.additionalimage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "additional_images")
data class LocalAdditionalImage (
    @PrimaryKey val additionalImageId: Int,
    val additionalImageSrc: String,
)