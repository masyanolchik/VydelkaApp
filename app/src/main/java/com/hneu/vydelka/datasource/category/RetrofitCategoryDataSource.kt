package com.hneu.vydelka.datasource.category

import com.hneu.core.datasource.category.RemoteCategoryDataSource
import com.hneu.core.domain.product.Category
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.network.categories.CategoryApiService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import javax.inject.Inject
import retrofit2.Callback
import retrofit2.Response

class RetrofitCategoryDataSource @Inject constructor(private val categoryApiService: CategoryApiService) :
    RemoteCategoryDataSource {
    override fun getCategories(): Flow<Result<List<Category>>> {
        return callbackFlow {
            val call: Call<List<Category>> = categoryApiService.getCategories()
            call.enqueue(object : Callback<List<Category>> {
                override fun onResponse(
                    call: Call<List<Category>>,
                    response: Response<List<Category>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            trySendBlocking(Result.Success(it))
                        }
                    } else {
                        response.errorBody()?.let {
                            trySendBlocking(Result.Error(IllegalStateException(it.string())))
                        }
                    }
                    close()
                }

                override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                    trySendBlocking(Result.Error(t))
                    close()
                }
            }
            )
            awaitClose()
        }
    }
}