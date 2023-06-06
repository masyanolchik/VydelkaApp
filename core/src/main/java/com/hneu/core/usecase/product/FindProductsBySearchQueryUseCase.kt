package com.hneu.core.usecase.product

import com.hneu.core.domain.product.Product
import com.hneu.core.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

class FindProductsBySearchQueryUseCase(private val productRepository: ProductRepository) {
    operator fun invoke(searchQuery: String): Flow<Result<List<Product>>> = productRepository.searchByQuery(searchQuery)
}