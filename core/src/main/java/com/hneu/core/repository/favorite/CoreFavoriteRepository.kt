package com.hneu.core.repository.favorite

import com.hneu.core.datasource.favorite.LocalFavoriteDataSource
import com.hneu.core.domain.product.Product
import com.hneu.core.repository.favorite.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class CoreFavoriteRepository(private val localDataSource: LocalFavoriteDataSource) : FavoriteRepository {
    override fun addToFavorites(product: Product) = localDataSource.addToFavorites(product)

    override fun fetchFavoriteProducts() = localDataSource.getFavorites()

    override fun removeFromFavorites(product: Product) = localDataSource.removeFromFavorites(product)
}