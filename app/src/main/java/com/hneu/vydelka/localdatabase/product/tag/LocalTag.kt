package com.hneu.vydelka.localdatabase.product.tag

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hneu.core.domain.product.Tag

@Entity(tableName = "tags")
data class LocalTag(
    @PrimaryKey val tagId: Int,
    @ColumnInfo(name = "name") val name: String,
)

fun LocalTag.toDomain() = Tag(tagId, name)

fun Tag.toLocal() = LocalTag(id, name)