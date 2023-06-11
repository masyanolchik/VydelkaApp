package com.hneu.vydelka.network.profile

import com.hneu.core.domain.user.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ProfileApiService {
    @POST("profile/change_contacts")
    fun changeContacts(@Body user: User): Call<User>
}