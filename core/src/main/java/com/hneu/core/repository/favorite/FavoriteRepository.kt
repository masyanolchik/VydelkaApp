package com.hneu.core.repository.favorite

import com.hneu.core.domain.product.Product
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface FavoriteRepository {
    fun addToFavorites(userId:Int, product: Product): Flow<Result<Product>>
    fun fetchFavoriteProducts(userId: Int): Flow<Result<List<Product>>>
    fun removeFromFavorites(userId: Int, product: Product): Flow<Result<Product>>
}