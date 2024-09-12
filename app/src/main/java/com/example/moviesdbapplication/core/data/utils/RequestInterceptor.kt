package com.example.moviesdbapplication.core.data.utils

import android.util.Log
import com.example.moviesdbapplication.utils.SharedPrefs
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

//    private fun getNewToken(): AuthenticationServices {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BuildConfig.API_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//        return retrofit.create(AuthenticationServices::class.java)
//    }
}