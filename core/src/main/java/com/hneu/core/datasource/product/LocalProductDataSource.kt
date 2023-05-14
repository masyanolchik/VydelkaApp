package com.hneu.core.datasource.product

import com.hneu.core.domain.product.Product
import com.hneu.core.domain.product.Tag
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface LocalProductDataSource {
    fun saveProduct(product: Product): Flow<Result>
    fun saveProducts(products: List<Product>): Flow<Result>
    fun getProducts(): Flow<Result>
    fun getProductsByCategoryId(categoryId: Int): Flow<Result>
    fun getProductsByTags(tags: List<Tag>): Flow<Result>
}