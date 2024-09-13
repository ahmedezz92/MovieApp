package com.example.moviesdbapplication.core.data.utils

import com.example.moviesdbapplication.utils.Constants.Authorization.TOKEN_BEARER
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", TOKEN_BEARER)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(newRequest)
    }

}