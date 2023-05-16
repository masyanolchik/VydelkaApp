package com.hneu.core.repository.category

import com.hneu.core.datasource.category.LocalCategoryDataSource
import com.hneu.core.datasource.category.RemoteCategoryDataSource
import com.hneu.core.domain.product.Category
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalCoroutinesApi::class)
class CoreCategoryRepository(
    private val localDataSource: LocalCategoryDataSource,
    private val remoteDataSource: RemoteCategoryDataSource,
) : CategoryRepository {
    override fun fetchCategories(): Flow<Result<List<Category>>> {
        return remoteDataSource
            .getCategories()
            .flatMapLatest { result ->
                when(result) {
                    is Result.Success -> localDataSource.saveCategories(result.data)
                    else -> flowOf(result)
                }
            }
    }
}