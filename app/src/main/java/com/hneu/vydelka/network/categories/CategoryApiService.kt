package com.hneu.vydelka.network.categories

import com.hneu.core.domain.product.Category
import com.hneu.core.domain.promo.Promo
import retrofit2.Call
import retrofit2.http.GET

interface CategoryApiService {
    @GET("category/get_all")
    fun getCategories(): Call<List<Category>>
}