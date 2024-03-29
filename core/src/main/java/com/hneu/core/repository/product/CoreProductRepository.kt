package com.hneu.core.repository.product

import com.hneu.core.datasource.product.LocalProductDataSource
import com.hneu.core.datasource.product.RemoteProductDataSource
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.product.Tag
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
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

    override fun fetchSortedProductsByCategoryId(
        sortOrderOrdinal: Int,
        categoryId: Int
    ): Flow<Result<List<Product>>> {
        return remoteDataSource
            .getSortedProducts(sortOrderOrdinal)
            .flatMapLatest { result ->
                when(result) {
                    is Result.Success -> {
                        localDataSource.saveProducts(result.data)
                    }
                    else -> flowOf(result)
                }
            }.combine(localDataSource.getProductsByCategoryId(categoryId)) { sortedItems, categorisedItems ->
                if(sortedItems is Result.Success && categorisedItems is Result.Success) {
                    val dataSorted = sortedItems.data
                    val dataCategorised = categorisedItems.data
                    Result.Success(dataSorted.filter {productSorted ->
                        dataCategorised.map { it.id }.contains(productSorted.id)
                    })
                } else {
                    Result.Completed()
                }
            }
    }

    override fun getProductById(productId: Int): Flow<Result<Product>> {
        return fetchProducts()
            .flatMapLatest { result ->
                when(result) {
                    is Result.Success -> {
                        localDataSource.getProductById(productId)
                    }
                    is Result.Completed -> flowOf(Result.Completed())
                    is Result.Error -> flowOf(Result.Error(result.throwable))
                    is Result.Loading -> flowOf(Result.Loading())
                }
            }
    }

    override fun getTopProducts(): Flow<Result<List<Product>>> {
        return remoteDataSource.getTopProducts()
            .flatMapLatest { result ->
                when(result) {
                    is Result.Success -> {
                        val listOfProducts = mutableListOf<Product>()
                        result.data.forEach {product ->
                            val productSave = localDataSource.saveProduct(product).first()
                            if(productSave is Result.Success) {
                                listOfProducts.add(productSave.data)
                            }
                        }
                        flowOf(Result.Success(listOfProducts))
                    }
                    else -> flowOf(result)
                }
            }
    }

    override fun getProductsByCategoryId(categoryId: Int): Flow<Result<List<Product>>> {
        return fetchProducts()
            .flatMapLatest {result ->
                when(result) {
                    is Result.Success -> {
                        localDataSource.getProductsByCategoryId(categoryId)
                    }
                    is Result.Completed -> flowOf(Result.Completed())
                    is Result.Error -> flowOf(Result.Error(result.throwable))
                    is Result.Loading -> flowOf(Result.Loading())
                }
            }

    }

    override fun getProductsByTags(tags: List<Tag>): Flow<Result<List<Product>>> {
        return fetchProducts()
            .flatMapLatest { result ->
                when(result) {
                    is Result.Success -> {
                        localDataSource.getProductsByTags(tags)
                    }
                   else -> flowOf(result)
                }
            }
    }

    override fun searchByQuery(searchQuery: String): Flow<Result<List<Product>>> {
        return remoteDataSource.searchByQuery(searchQuery)
            .flatMapLatest { result ->
                when(result) {
                    is Result.Success -> {
                        localDataSource.saveProducts(result.data)
                    }
                    else -> flowOf(result)
                }
            }
    }
}