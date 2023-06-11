package com.hneu.vydelka.network.promo

import com.hneu.core.domain.promo.Promo
import retrofit2.Call
import retrofit2.http.GET

interface PromoApiService {
    @GET("promo/get_all")
    fun getPromos(): Call<List<Promo>>
}