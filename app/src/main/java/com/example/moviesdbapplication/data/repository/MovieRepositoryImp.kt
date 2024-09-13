package com.example.moviesdbapplication.data.repository

import com.example.moviesdbapplication.base.BaseResult
import com.example.moviesdbapplication.core.WrappedResponse
import com.example.moviesdbapplication.data.remote.api.MovieApiService
import com.example.moviesdbapplication.data.remote.model.MovieResponse
import com.example.moviesdbapplication.domain.repository.MovieRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieApiService: MovieApiService,
) :
    MovieRepository {
    override suspend fun getUpcomingMovies(): Flow<BaseResult<MovieResponse>> {
        return flow {
            val response = movieApiService.getUpcomingMovies()
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))
            }  else {
                val errorBody = response.errorBody()?.charStream()
                val type = object : TypeToken<WrappedResponse<MovieResponse>>() {}.type
                val errorResponse: WrappedResponse<MovieResponse> = Gson().fromJson(errorBody, type)
                emit(BaseResult.ErrorState(response.code()))
            }
        }    }

    override suspend fun getPopularMovies(): Flow<BaseResult<MovieResponse>> {
        return flow {
            val response = movieApiService.getPopularMovies()
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))
            } else {
                val errorBody = response.errorBody()?.charStream()
                val type = object : TypeToken<WrappedResponse<MovieResponse>>() {}.type
                val errorResponse = Gson().fromJson<WrappedResponse<MovieResponse>>(errorBody, type)
                emit(BaseResult.ErrorState(response.code()))
            }
        }    }

    override suspend fun getNowPlayingMovies(): Flow<BaseResult<MovieResponse>> {
        return flow {
            val response = movieApiService.getNowPlayingMovies()
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))
            } else {
                val errorBody = response.errorBody()?.charStream()
                val type = object : TypeToken<WrappedResponse<MovieResponse>>() {}.type
                val errorResponse = Gson().fromJson<WrappedResponse<MovieResponse>>(errorBody, type)
                emit(BaseResult.ErrorState(response.code()))
            }
        }    }


}