package com.example.moviesdbapplication.domain.usecase

import com.example.moviesdbapplication.base.BaseResult
import com.example.moviesdbapplication.data.remote.model.MovieResponse
import com.example.moviesdbapplication.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingMoviesUseCase  @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun execute(): Flow<BaseResult<MovieResponse>> {
        return movieRepository.getUpcomingMovies()
    }
}