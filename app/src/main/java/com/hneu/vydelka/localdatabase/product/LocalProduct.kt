package com.hneu.vydelka.localdatabase.product

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.hneu.vydelka.localdatabase.product.category.LocalCategory

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
    val dateOfOrder: String,
    val description: String,
    val titleImageSrc: String,
)