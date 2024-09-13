package com.example.moviesdbapplication

import com.example.moviesdbapplication.base.BaseResult
import com.example.moviesdbapplication.core.WrappedResponse
import com.example.moviesdbapplication.data.remote.api.MovieApiService
import com.example.moviesdbapplication.data.repository.MovieRepositoryImp
import com.example.moviesdbapplication.data.remote.model.MovieResponse
import com.example.moviesdbapplication.data.remote.model.ResultResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

data class MockMovieResultResponse(val id: Int, val title: String)

data class MockMovieRepoResponse(
    val id: Int,
    val pages: Int,
    val total_pages: Int,
    val moviesList: List<MockMovieResultResponse>
)

class MovieRepositoryImpTest {

    @Mock
    private lateinit var movieApiService: MovieApiService

    private lateinit var movieRepository: MovieRepositoryImp

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieRepository = MovieRepositoryImp(movieApiService)
    }

    private fun createMockResponse(): Response<MovieResponse> {
        val movieResponse = MovieResponse(
            page = 1,
            total_pages = 10,
            total_results = 100,
            results = listOf(
                ResultResponse(
                    adult = false,
                    backdrop_path = "/path.jpg",
                    id = 1,
                    original_language = "en",
                    original_title = "SpiderMan",
                    overview = "Overview",
                    popularity = 7.5,
                    poster_path = "/poster.jpg",
                    release_date = "2024-01-01",
                    title = "Title",
                    video = false,
                    vote_average = 8.0,
                    vote_count = 100
                )
            )
        )
        return Response.success(movieResponse)
    }

    @Test
    fun `test getUpcomingMovies returns success`() = runTest {
        // Arrange
        val response = createMockResponse()
        whenever(movieApiService.getUpcomingMovies()).thenReturn(response)

        // Act
        val resultFlow = movieRepository.getUpcomingMovies()

        // Assert
        resultFlow.collect { result ->
            assertTrue(result is BaseResult.DataState)
            assertEquals((result as BaseResult.DataState).items, response.body())
        }
    }

    @Test
    fun `test getUpcomingMovies returns error`() = runTest {
        // Arrange
        val errorResponse = Gson().toJson(
            WrappedResponse<MovieResponse>(code = 404, page = 0, data = null)
        )
        val response = Response.error<MovieResponse>(404, errorResponse.toResponseBody())

        whenever(movieApiService.getUpcomingMovies()).thenReturn(response)

        // Act
        val resultFlow = movieRepository.getUpcomingMovies()

        // Assert
        resultFlow.collect { result ->
            assertTrue(result is BaseResult.ErrorState)
            assertEquals((result as BaseResult.ErrorState).errorCode, 404)
        }
    }

}
