package com.hneu.vydelka.network.order

import com.hneu.core.domain.order.Order
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApiService {
    @POST("order/save_order")
    fun saveOrder(@Body order: OrderSaveRequestDataModel): Call<OrderResponseDataModel>
}