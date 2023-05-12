package com.hneu.core.datasource.category

import com.hneu.core.domain.product.Category
import kotlinx.coroutines.flow.Flow

interface RemoteCategoryDataSource {
    fun getCategories(): Flow<Result<List<Category>>>
}