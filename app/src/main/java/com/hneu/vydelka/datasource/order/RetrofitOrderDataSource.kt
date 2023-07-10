package com.hneu.vydelka.datasource.order

import com.hneu.core.datasource.order.RemoteOrderDataSource
import com.hneu.core.domain.order.Order
import com.hneu.core.domain.request.Result
import com.hneu.core.domain.user.User
import com.hneu.vydelka.network.order.OrderApiService
import com.hneu.vydelka.network.order.OrderResponseDataModel
import com.hneu.vydelka.network.order.toDomain
import com.hneu.vydelka.network.order.toNetwork
import com.hneu.vydelka.network.product.toDomain
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetrofitOrderDataSource @Inject constructor(private val orderApiService: OrderApiService) : RemoteOrderDataSource {
    override fun saveOrder(order: Order, user: User?): Flow<Result<Order>> {
        return callbackFlow {
            val call: Call<OrderResponseDataModel> = orderApiService.saveOrder(order.toNetwork())
            call.enqueue(object: Callback<OrderResponseDataModel> {
                override fun onResponse(call: Call<OrderResponseDataModel>, response: Response<OrderResponseDataModel>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            trySendBlocking(Result.Completed())
                        }
                    } else {
                        response.errorBody()?.let {
                            trySendBlocking(Result.Error(IllegalStateException(it.string())))
                        }
                    }
                    close()
                }

                override fun onFailure(call: Call<OrderResponseDataModel>, t: Throwable) {
                    trySendBlocking(Result.Error(t))
                    close()
                }

            })
            awaitClose()
        }
    }

    override fun getOrders(user: User): Flow<Result<List<Order>>> {
        return flowOf(Result.Completed())
    }
}