package com.hneu.core.usecase.favorite

import com.hneu.core.repository.favorite.FavoriteRepository

class FetchFavoriteProductsUseCase(private val favoriteRepository: FavoriteRepository) {
    operator fun invoke(userId: Int) = favoriteRepository.fetchFavoriteProducts(userId)
}