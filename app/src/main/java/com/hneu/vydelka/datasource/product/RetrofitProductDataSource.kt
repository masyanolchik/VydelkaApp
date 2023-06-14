package com.hneu.vydelka.datasource.product

import com.hneu.core.datasource.product.RemoteProductDataSource
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.network.product.ProductApiService
import com.hneu.vydelka.network.product.ProductResponseDataModel
import com.hneu.vydelka.network.product.toDomain
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetrofitProductDataSource @Inject constructor(private val productApiService: ProductApiService) : RemoteProductDataSource {
    override fun getProducts(): Flow<Result<List<Product>>> {
        return callbackFlow {
            val call: Call<List<ProductResponseDataModel>> = productApiService.getAllProducts()
            call.enqueue(object : Callback<List<ProductResponseDataModel>> {
                override fun onResponse(
                    call: Call<List<ProductResponseDataModel>>,
                    response: Response<List<ProductResponseDataModel>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            trySendBlocking(Result.Success(it.map { it.toDomain() }))
                        }
                    } else {
                        response.errorBody()?.let {
                            trySendBlocking(Result.Error(IllegalStateException(it.string())))
                        }
                    }
                    close()
                }

                override fun onFailure(call: Call<List<ProductResponseDataModel>>, t: Throwable) {
                    trySendBlocking(Result.Error(t))
                    close()
                }

            })
            awaitClose()
        }
    }

    override fun getSortedProducts(sortOrderOrdinal: Int): Flow<Result<List<Product>>> {
        return callbackFlow {
            val call: Call<List<ProductResponseDataModel>> = productApiService.getSortedProducts(sortOrderOrdinal)
            call.enqueue(object : Callback<List<ProductResponseDataModel>> {
                override fun onResponse(
                    call: Call<List<ProductResponseDataModel>>,
                    response: Response<List<ProductResponseDataModel>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            trySendBlocking(Result.Success(it.map { it.toDomain() }))
                        }
                    } else {
                        response.errorBody()?.let {
                            trySendBlocking(Result.Error(IllegalStateException(it.string())))
                        }
                    }
                    close()
                }

                override fun onFailure(call: Call<List<ProductResponseDataModel>>, t: Throwable) {
                    trySendBlocking(Result.Error(t))
                    close()
                }
            })
            awaitClose()
        }
    }

    override fun getTopProducts(): Flow<Result<List<Product>>> {
        return callbackFlow {
            val call: Call<List<ProductResponseDataModel>> = productApiService.getTopProducts()
            call.enqueue(object : Callback<List<ProductResponseDataModel>> {
                override fun onResponse(
                    call: Call<List<ProductResponseDataModel>>,
                    response: Response<List<ProductResponseDataModel>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            trySendBlocking(Result.Success(it.map { it.toDomain() }))
                        }
                    } else {
                        response.errorBody()?.let {
                            trySendBlocking(Result.Error(IllegalStateException(it.string())))
                        }
                    }
                    close()
                }

                override fun onFailure(call: Call<List<ProductResponseDataModel>>, t: Throwable) {
                    trySendBlocking(Result.Error(t))
                    close()
                }

            })
            awaitClose()
        }
    }

    override fun searchByQuery(searchQuery: String): Flow<Result<List<Product>>> {
        return callbackFlow {
            val call: Call<List<ProductResponseDataModel>> = productApiService.searchProducts(searchQuery)
            call.enqueue(object : Callback<List<ProductResponseDataModel>> {
                override fun onResponse(
                    call: Call<List<ProductResponseDataModel>>,
                    response: Response<List<ProductResponseDataModel>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            trySendBlocking(Result.Success(it.map { it.toDomain() }))
                        }
                    } else {
                        response.errorBody()?.let {
                            trySendBlocking(Result.Error(IllegalStateException(it.string())))
                        }
                    }
                    close()
                }

                override fun onFailure(call: Call<List<ProductResponseDataModel>>, t: Throwable) {
                    trySendBlocking(Result.Error(t))
                    close()
                }

            })
            awaitClose()
        }
    }
}