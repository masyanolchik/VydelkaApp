package com.hneu.core.repository.product

import com.hneu.core.datasource.product.LocalProductDataSource
import com.hneu.core.datasource.product.RemoteProductDataSource
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.product.Tag
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalCoroutinesApi::class)
class CoreProductRepository(
    private val localDataSource: LocalProductDataSource,
    private val remoteDataSource: RemoteProductDataSource,
) : ProductRepository {
    override fun fetchProducts(): Flow<Result<List<Product>>> {
        return remoteDataSource
            .getProducts()
            .flatMapLatest { result ->
                when(result) {
                    is Result.Success -> {
                        localDataSource.saveProducts(result.data)
                    }
                    else -> flowOf(result)
                }
            }
    }

    override fun getProductsByCategoryId(categoryId: Int): Flow<Result<List<Product>>> {
        return localDataSource.getProductsByCategoryId(categoryId)
    }

    override fun getProductsByTags(tags: List<Tag>): Flow<Result<List<Product>>> {
        return localDataSource.getProductsByTags(tags)
    }
}