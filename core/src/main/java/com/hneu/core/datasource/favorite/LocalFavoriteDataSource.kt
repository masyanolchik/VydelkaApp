package com.hneu.core.datasource.favorite

import com.hneu.core.domain.product.Product
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface LocalFavoriteDataSource {
    fun addToFavorites(product: Product): Flow<Result>
    fun getFavorites(): Flow<Result>
    fun removeFromFavorites(product: Product): Flow<Result>
}