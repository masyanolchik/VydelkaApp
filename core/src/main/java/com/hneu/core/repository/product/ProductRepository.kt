package com.hneu.core.repository.product

import com.hneu.core.domain.product.Product
import com.hneu.core.domain.product.Tag
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface ProductRepository {
    fun fetchProducts(): Flow<Result<List<Product>>>
    fun getProductById(productId: Int): Flow<Result<Product>>
    fun getTopProducts(): Flow<Result<List<Product>>>
    fun getProductsByCategoryId(categoryId: Int): Flow<Result<List<Product>>>
    fun getProductsByTags(tags: List<Tag>): Flow<Result<List<Product>>>
}