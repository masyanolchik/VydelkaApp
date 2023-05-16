package com.hneu.core.datasource.category

import com.hneu.core.domain.product.Category
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface LocalCategoryDataSource {
    fun saveCategories(categories: List<Category>): Flow<Result<List<Category>>>
    fun getCategories(): Flow<Result<List<Category>>>
}