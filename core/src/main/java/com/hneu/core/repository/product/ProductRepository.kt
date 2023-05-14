package com.hneu.core.repository.product

import com.hneu.core.domain.product.Product
import com.hneu.core.domain.product.Tag
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun fetchProducts(): Flow<Result<List<Product>>>
    fun fetchProductsByCategoryId(categoryId: Int): Flow<Result<List<Product>>>
    fun fetchProductsByTags(tags: List<Tag>): Flow<Result<List<Product>>>
}