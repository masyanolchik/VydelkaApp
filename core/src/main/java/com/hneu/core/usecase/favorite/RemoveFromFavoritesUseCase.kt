package com.hneu.core.usecase.favorite

import com.hneu.core.domain.product.Product
import com.hneu.core.repository.favorite.FavoriteRepository

class RemoveFromFavoritesUseCase(private val favoritesRepository: FavoriteRepository) {
    operator fun invoke(userId: Int, product: Product) = favoritesRepository.removeFromFavorites(userId, product)
}