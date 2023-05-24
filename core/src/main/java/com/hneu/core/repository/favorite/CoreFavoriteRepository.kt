package com.hneu.core.repository.favorite

import com.hneu.core.datasource.favorite.LocalFavoriteDataSource
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.request.Result
import com.hneu.core.repository.favorite.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalCoroutinesApi::class)
class CoreFavoriteRepository(private val localDataSource: LocalFavoriteDataSource) : FavoriteRepository {
    override fun addToFavorites(userId: Int, product: Product): Flow<Result<Product>> =
        localDataSource.addToFavorites(userId, product)

    override fun fetchFavoriteProducts(userId: Int): Flow<Result<List<Product>>> =
        localDataSource.getFavorites(userId)

    override fun removeFromFavorites(userId: Int, product: Product): Flow<Result<Product>> =
        localDataSource.removeFromFavorites(userId, product)
}