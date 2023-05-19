package com.hneu.vydelka.datasource.category

import com.hneu.core.datasource.category.LocalCategoryDataSource
import com.hneu.core.domain.product.Category
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.localdatabase.product.category.CategoryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCategoryDataSource @Inject constructor(private val categoryDao: CategoryDao): LocalCategoryDataSource{
    override fun saveCategories(categories: List<Category>): Flow<Result<List<Category>>> {
        TODO("Not yet implemented")
    }

    override fun getCategories(): Flow<Result<List<Category>>> {
        TODO("Not yet implemented")
    }
}