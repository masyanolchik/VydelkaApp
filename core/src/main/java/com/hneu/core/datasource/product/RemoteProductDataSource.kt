package com.hneu.core.datasource.product

import com.hneu.core.domain.product.Product
import com.hneu.core.domain.product.Tag
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface RemoteProductDataSource {
    fun getProducts(): Flow<Result<List<Product>>>
    fun getSortedProducts(sortOrderOrdinal: Int): Flow<Result<List<Product>>>
    fun getTopProducts(): Flow<Result<List<Product>>>
    fun searchByQuery(searchQuery: String): Flow<Result<List<Product>>>

    companion object {
        const val LOW_PRICE_SORT = 0
        const val HIGH_PRICE_SORT = 1
    }
}