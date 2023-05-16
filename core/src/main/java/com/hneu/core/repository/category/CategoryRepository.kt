package com.hneu.core.repository.category

import com.hneu.core.domain.product.Category
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface CategoryRepository {
    fun fetchCategories(): Flow<Result<List<Category>>>
}