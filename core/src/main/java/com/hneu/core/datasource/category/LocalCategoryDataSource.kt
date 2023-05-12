package com.hneu.core.datasource.category

import com.hneu.core.domain.product.Category
import kotlinx.coroutines.flow.Flow

interface LocalCategoryDataSource {
    fun saveCategories(categories: List): Flow<Result<List<Category>>>
    fun getCategories(): Flow<Result<List<Category>>>
}