package com.hneu.core.datasource.favorite

import com.hneu.core.domain.product.Product
import kotlinx.coroutines.flow.Flow

interface LocalFavoriteDataSource {
    fun addToFavorites(product: Product): Flow<Result<Product>>
    fun getFavorites(): Flow<Result<List<Product>>>
    fun removeFromFavorites(product: Product): Flow<Result<Product>>
}