package com.hneu.core.repository.category

import com.hneu.core.domain.product.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun fetchCategories(): Flow<Result<Category>>
}