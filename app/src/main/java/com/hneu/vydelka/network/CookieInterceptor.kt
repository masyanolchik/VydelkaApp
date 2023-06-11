package com.hneu.vydelka.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CookieInterceptor : Interceptor {
    private var cookie: String = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val modifiedRequest: Request = originalRequest.newBuilder()
            .header("Cookie", cookie)
            .build()

        val response: Response = chain.proceed(modifiedRequest)

        val newCookies: List<String> = response.headers("Set-Cookie")
        if (newCookies.isNotEmpty()) {
            cookie = newCookies[0]
        }

        return response
    }
}