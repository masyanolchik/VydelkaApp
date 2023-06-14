package com.hneu.vydelka.network.product

import com.hneu.core.domain.product.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {
    @GET("product/get_all")
    fun getAllProducts(): Call<List<ProductResponseDataModel>>

    @GET("product/get_sorted/{sortingOrder}")
    fun getSortedProducts(@Path("sortingOrder") sortingOrder: Int): Call<List<ProductResponseDataModel>>

    @GET("product/get_top_products")
    fun getTopProducts(): Call<List<ProductResponseDataModel>>

    @GET("product/get_product_by_query/{query}")
    fun searchProducts(@Path("query") query: String): Call<List<ProductResponseDataModel>>
}