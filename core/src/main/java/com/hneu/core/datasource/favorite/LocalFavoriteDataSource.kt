package com.hneu.core.datasource.favorite

import com.hneu.core.domain.product.Product
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface LocalFavoriteDataSource {
    fun addToFavorites(userId: Int, product: Product): Flow<Result<Product>>
    fun getFavorites(userId: Int): Flow<Result<List<Product>>>
    fun removeFromFavorites(userId: Int, product: Product): Flow<Result<Product>>
}