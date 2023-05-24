package com.hneu.core.usecase.favorite

import com.hneu.core.domain.product.Product
import com.hneu.core.repository.favorite.FavoriteRepository

class AddToFavoritesUseCase(private val favoriteRepository: FavoriteRepository) {
    operator fun invoke(userId: Int, product: Product) = favoriteRepository.addToFavorites(userId, product)
}