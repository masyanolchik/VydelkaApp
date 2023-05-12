package com.hneu.core.datasource.product

import com.hneu.core.domain.product.Product
import kotlinx.coroutines.flow.Flow

interface RemoteProductDataSource {
    fun getProducts(): Flow<Result<List<Product>>>
    fun getProductsByCategoryId(categoryId: Int): Flow<Result<List<Product>>>
}