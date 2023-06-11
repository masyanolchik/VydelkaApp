package com.hneu.vydelka.datasource.promo

import com.hneu.core.datasource.promo.RemotePromoDataSource
import com.hneu.core.domain.promo.Promo
import com.hneu.core.domain.request.Result
import com.hneu.core.domain.user.User
import com.hneu.vydelka.network.promo.PromoApiService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetrofitPromoDataSource @Inject constructor(private val promoApiService: PromoApiService): RemotePromoDataSource {
    override fun getPromos(): Flow<Result<List<Promo>>> {
        return callbackFlow {
            val call: Call<List<Promo>> = promoApiService.getPromos()
            call.enqueue(object : Callback<List<Promo>> {
                override fun onResponse(call: Call<List<Promo>>, response: Response<List<Promo>>) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            trySendBlocking(Result.Success(it.map {
                                Promo(
                                    id = it.id,
                                    name = it.name,
                                    detailedDescription = it.detailedDescription,
                                    tags = it.tags,
                                    promoImageSrc = "http://192.168.31.114:8080"+it.promoImageSrc
                                )
                            }))
                        }
                    } else {
                        response.errorBody()?.let {
                            trySendBlocking(Result.Error(IllegalStateException(it.string())))
                        }
                    }
                    close()
                }

                override fun onFailure(call: Call<List<Promo>>, t: Throwable) {
                    trySendBlocking(Result.Error(t))
                    close()
                }

            })
            awaitClose()
        }
    }
}