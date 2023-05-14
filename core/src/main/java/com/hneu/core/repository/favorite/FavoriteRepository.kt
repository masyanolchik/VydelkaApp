package com.hneu.core.repository.favorite

import com.hneu.core.domain.product.Product
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun addToFavorites(product: Product): Flow<Result<Product>>
    fun fetchFavoriteProducts(): Flow<Result<List<Product>>>
    fun removeFromFavorites(product: Product): Flow<Result<Product>>
}