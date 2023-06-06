package com.hneu.core.usecase.product

import com.hneu.core.repository.product.ProductRepository

class FetchSortedProductsByCategoryIdUseCase(private val productRepository: ProductRepository) {
    operator fun invoke(sortOrderOrdinal: Int, categoryId: Int) = productRepository.fetchSortedProductsByCategoryId(sortOrderOrdinal, categoryId)
}