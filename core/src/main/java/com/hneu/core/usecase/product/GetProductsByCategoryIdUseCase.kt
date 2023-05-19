package com.hneu.core.usecase.product

import com.hneu.core.repository.product.ProductRepository

class GetProductsByCategoryIdUseCase(private val productRepository: ProductRepository) {
    operator fun invoke(categoryId: Int) = productRepository.getProductsByCategoryId(categoryId)
}