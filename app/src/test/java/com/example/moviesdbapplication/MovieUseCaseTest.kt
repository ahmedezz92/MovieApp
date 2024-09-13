package com.example.moviesdbapplication

import com.example.moviesdbapplication.base.BaseResult
import com.example.moviesdbapplication.domain.repository.MovieRepository
import com.example.moviesdbapplication.domain.usecase.GetNowPlayingMoviesUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

// Mocked data classes and repository
data class MockMovieResponse(val id: Int, val title: String)

class GetNowPlayingMoviesUseCaseTest {

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(movieRepository)
    }

    @Test
    fun `test execute returns success`() = runTest {
        // Arrange
        val movieResponse = MockMovieResponse(id = 1, title = "Now Playing Movie")
        val baseResult = BaseResult.DataState(movieResponse)

        // Mock the Flow response
        whenever(movieRepository.getNowPlayingMovies()).thenAnswer {
            flow {
                emit(baseResult)
            }
        }

        // Act
        val resultFlow = getNowPlayingMoviesUseCase.execute()

        // Assert
        resultFlow.collect { result ->
            if (result is BaseResult.DataState) {
                assertEquals(result.items, movieResponse)
            } else {
                // Fail the test if the result is not DataState
                throw AssertionError("Expected DataState but got $result")
            }
        }
    }

    @Test
    fun `test execute returns error`() = runTest {
        // Arrange
        val errorCode = 404
        val errorMessage = "Not Found"
        val baseResult = BaseResult.ErrorState(errorCode)

        // Mock the Flow response
        whenever(movieRepository.getNowPlayingMovies()).thenAnswer {
            flow {
                emit(baseResult)
            }
        }

        // Act
        val resultFlow = getNowPlayingMoviesUseCase.execute()

        // Assert
        resultFlow.collect { result ->
            if (result is BaseResult.ErrorState) {
                assertEquals(result.errorCode, errorCode)
            } else {
                // Fail the test if the result is not ErrorState
                throw AssertionError("Expected ErrorState but got $result")
            }
        }
    }
}
