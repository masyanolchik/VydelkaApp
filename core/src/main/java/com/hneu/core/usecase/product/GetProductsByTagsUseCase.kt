package com.hneu.core.usecase.product

import com.hneu.core.domain.product.Tag
import com.hneu.core.repository.product.ProductRepository

class GetProductsByTagsUseCase(private val productRepository: ProductRepository) {
    operator fun invoke(list: List<Tag>) = productRepository.getProductsByTags(list)
}