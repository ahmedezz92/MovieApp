package com.example.moviesdbapplication.domain.repository

import com.example.moviesdbapplication.base.BaseResult
import com.example.moviesdbapplication.data.remote.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getUpcomingMovies(): Flow<BaseResult<MovieResponse>>
    suspend fun getPopularMovies(): Flow<BaseResult<MovieResponse>>
    suspend fun getNowPlayingMovies(): Flow<BaseResult<MovieResponse>>
}