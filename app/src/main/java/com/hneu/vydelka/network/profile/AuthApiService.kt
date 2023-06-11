package com.hneu.vydelka.network.profile

import com.hneu.core.domain.user.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthApiService {
    @POST("auth/signin")
    fun loginUser(@Body loginDataModel: LoginDataModel): Call<User>

    @POST("auth/signup")
    fun register(@Body user: User): Call<Void>

    @POST("auth/change_password")
    fun changePassword(@Body user: User): Call<Void>

    @GET("auth/signout")
    fun signOut(): Call<Void>
}
