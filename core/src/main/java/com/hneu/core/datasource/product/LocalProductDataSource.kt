package com.hneu.core.datasource.product

import com.hneu.core.domain.product.Product
import com.hneu.core.domain.product.Tag
import kotlinx.coroutines.flow.Flow

interface LocalProductDataSource {
    fun saveProduct(product: Product): Flow<List<Product>>
    fun saveProducts(products: List<Product>): Flow<List<Product>>
    fun getProducts(): Flow<Result<List<Product>>>
    fun getProductsByCategoryId(categoryId: Int): Flow<Result<List<Product>>>
    fun getProductsByTags(tags: List<Tag>): Flow<Result<List<Product>>>
}