package com.hneu.core.datasource.product

import com.hneu.core.domain.product.Product
import com.hneu.core.domain.product.Tag
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface RemoteProductDataSource {
    fun getProducts(): Flow<Result>
    fun getProductsByCategoryId(categoryId: Int): Flow<Result>
    fun getProductsByTags(tags: List<Tag>): Flow<Result>
}