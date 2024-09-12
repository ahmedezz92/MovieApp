package com.example.moviesdbapplication.data.remote.api

import com.example.moviesdbapplication.data.remote.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieApiService {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<MovieResponse>
}
