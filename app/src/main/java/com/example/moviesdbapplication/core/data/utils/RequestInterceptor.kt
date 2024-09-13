package com.example.moviesdbapplication.core.data.utils

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(
) : Interceptor {
    private lateinit var token: String
    override fun intercept(chain: Interceptor.Chain): Response {
        token =
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2YWJlODMxNTNmYWRiYjk3ZWE3ZjIyMWZjYjM3NmE4YyIsIm5iZiI6MTcyNjEyNDQ0OS41MDU0MDgsInN1YiI6IjY2ZDljODQ0NmYwNGQ2Nzc5OWJmNzNhOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.67avmWu5BuqnQbW65Y5SwoyGCwXd9OCzWZ5T3pIbBwE"
        val newRequest = chain.request().newBuilder()
            .addHeader("authorization", token)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(newRequest)
    }

}