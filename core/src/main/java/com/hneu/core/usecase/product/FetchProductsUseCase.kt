package com.hneu.core.usecase.product

import com.hneu.core.repository.product.ProductRepository

class FetchProductsUseCase(private val productRepository: ProductRepository) {
    operator fun invoke() = productRepository.fetchProducts()
}