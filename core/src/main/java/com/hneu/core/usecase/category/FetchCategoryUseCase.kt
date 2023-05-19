package com.hneu.core.usecase.category

import com.hneu.core.repository.category.CategoryRepository

class FetchCategoryUseCase(private val categoryRepository: CategoryRepository) {
    operator fun invoke() = categoryRepository.fetchCategories()
}