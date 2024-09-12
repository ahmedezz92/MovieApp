package com.example.moviesdbapplication.presentation.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdbapplication.base.BaseResult
import com.example.moviesdbapplication.data.remote.model.MovieResponse
import com.example.moviesdbapplication.data.remote.model.ResultResponse
import com.example.moviesdbapplication.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.moviesdbapplication.domain.usecase.GetPopularMoviesUseCase
import com.example.moviesdbapplication.domain.usecase.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex

    private val _getMoviesState =
        MutableStateFlow<GetMoviesActivityState>(GetMoviesActivityState.Init)

    /*popular movies variables*/
    private val _popularMoviesState =
        MutableStateFlow<GetMoviesActivityState>(GetMoviesActivityState.Init)

    private val _popularMoviesList = MutableStateFlow<List<ResultResponse>>(emptyList())
    val popularMoviesList: StateFlow<List<ResultResponse>> = _popularMoviesList

    /*now playing movies variables*/
    private val _nowPlayingMoviesState =
        MutableStateFlow<GetMoviesActivityState>(GetMoviesActivityState.Init)

    private val _nowPlayingMoviesList = MutableStateFlow<List<ResultResponse>>(emptyList())
    val nowPlayingMoviesList: StateFlow<List<ResultResponse>> = _nowPlayingMoviesList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    /*upcoming movies variables*/
    private val _upcomingMoviesState =
        MutableStateFlow<GetMoviesActivityState>(GetMoviesActivityState.Init)

    private val _upcomingMoviesList = MutableStateFlow<List<ResultResponse>>(emptyList())
    val upcomingMoviesList: StateFlow<List<ResultResponse>> = _upcomingMoviesList

    private fun setLoading() {
        _getMoviesState.value = GetMoviesActivityState.IsLoading(true)
    }

    fun getUpcomingMovies(): Flow<GetMoviesActivityState> = flow {
        getUpcomingMoviesUseCase.execute()
            .onStart {
                _isLoading.value = true
            }
            .catch { error ->
                _isLoading.value = false
                emit(
                    GetMoviesActivityState.Error(
                        errorCode = -1,
                        errorMessage = error.message ?: "Unknown error occurred"
                    )
                )
            }
            .collect { result ->
                _isLoading.value = false
                when (result) {
                    is BaseResult.ErrorState -> {
                        val errorState =
                            GetMoviesActivityState.Error(result.errorCode, result.errorMessage)
                        _upcomingMoviesState.value = errorState
                        emit(errorState)
                    }

                    is BaseResult.DataState -> {
                        result.items?.let { movies ->
                            val successState = GetMoviesActivityState.Success(movies)
                            _upcomingMoviesState.value = successState
                            _upcomingMoviesList.value = successState.data.results
                            emit(successState)
                        } ?: emit(GetMoviesActivityState.Error(-1, "No movies data"))
                    }
                }
            }
    }

    fun getPopularMovies(): Flow<GetMoviesActivityState> = flow {
        getPopularMoviesUseCase.execute()
            .onStart {
                setLoading()
            }
            .catch { error ->
                emit(
                    GetMoviesActivityState.Error(
                        errorCode = -1,
                        errorMessage = error.message ?: "Unknown error occurred"
                    )
                )
            }
            .collect { result ->
                when (result) {
                    is BaseResult.ErrorState -> {
                        val errorState =
                            GetMoviesActivityState.Error(result.errorCode, result.errorMessage)
                        _upcomingMoviesState.value = errorState
                        emit(errorState)
                    }

                    is BaseResult.DataState -> {
                        result.items?.let { movies ->
                            val successState = GetMoviesActivityState.Success(movies)
                            _popularMoviesState.value = successState
                            _popularMoviesList.value = successState.data.results
                            emit(successState)
                        } ?: emit(GetMoviesActivityState.Error(-1, "No movies data"))
                    }
                }
            }
    }

    fun getNowPlayingMovies(): Flow<GetMoviesActivityState> = flow {
        getNowPlayingMoviesUseCase.execute()
            .onStart {
                setLoading()
            }
            .catch { error ->
                emit(
                    GetMoviesActivityState.Error(
                        errorCode = -1,
                        errorMessage = error.message ?: "Unknown error occurred"
                    )
                )
            }
            .collect { result ->
                when (result) {
                    is BaseResult.ErrorState -> {
                        val errorState =
                            GetMoviesActivityState.Error(result.errorCode, result.errorMessage)
                        _nowPlayingMoviesState.value = errorState
                        emit(errorState)
                    }

                    is BaseResult.DataState -> {
                        result.items?.let { movies ->
                            val successState = GetMoviesActivityState.Success(movies)
                            _nowPlayingMoviesState.value = successState
                            _nowPlayingMoviesList.value = successState.data.results
                            emit(successState)
                        } ?: emit(GetMoviesActivityState.Error(-1, "No movies data"))
                    }
                }
            }
    }

    fun handleStateUpcomingMovies(state: GetMoviesActivityState) {
        _upcomingMoviesState.value = state
        when (state) {
            is GetMoviesActivityState.Init -> Unit

            is GetMoviesActivityState.IsLoading -> {
                /* Show loading UI */
                setLoading()
            }

            is GetMoviesActivityState.Success -> { /* Update UI with movies */
                _getMoviesState.value = GetMoviesActivityState.IsLoading(false)
                _upcomingMoviesState.value = GetMoviesActivityState.Success(
                    state.data
                )
            }

            is GetMoviesActivityState.Error -> { /* Show error message */
            }

            is GetMoviesActivityState.ShowToast -> {}
        }
    }

    fun handleStatePopularMovies(state: GetMoviesActivityState) {
        _popularMoviesState.value = state
        when (state) {
            is GetMoviesActivityState.Init -> Unit

            is GetMoviesActivityState.IsLoading -> {
                /* Show loading UI */
                setLoading()
            }

            is GetMoviesActivityState.Success -> { /* Update UI with movies */
                _getMoviesState.value = GetMoviesActivityState.IsLoading(false)
                _popularMoviesState.value = GetMoviesActivityState.Success(
                    state.data
                )
            }

            is GetMoviesActivityState.Error -> { /* Show error message */
            }

            is GetMoviesActivityState.ShowToast -> {}
        }
    }

    fun handleStateNowPlayingMovies(state: GetMoviesActivityState) {
        _nowPlayingMoviesState.value = state
        when (state) {
            is GetMoviesActivityState.Init -> Unit

            is GetMoviesActivityState.IsLoading -> {
                /* Show loading UI */
                setLoading()
            }

            is GetMoviesActivityState.Success -> { /* Update UI with movies */
                _getMoviesState.value = GetMoviesActivityState.IsLoading(false)
                _nowPlayingMoviesState.value = GetMoviesActivityState.Success(
                    state.data
                )
            }

            is GetMoviesActivityState.Error -> { /* Show error message */
            }

            is GetMoviesActivityState.ShowToast -> {}
        }
    }


    fun onTabSelected(index: Int) {
        _selectedTabIndex.value = index
    }

    fun getMovieById(id: Int): ResultResponse? {
        return upcomingMoviesList.value.find { it.id == id }
            ?: popularMoviesList.value.find { it.id == id }
            ?: nowPlayingMoviesList.value.find { it.id == id }
    }



    sealed class GetMoviesActivityState {
        object Init : GetMoviesActivityState()
        data class IsLoading(val isLoading: Boolean) : GetMoviesActivityState()
        data class ShowToast(val message: String, val isConnectionError: Boolean) :
            GetMoviesActivityState()

        data class Success(val data: MovieResponse) :
            GetMoviesActivityState()

        data class Error(val errorCode: Int, val errorMessage: String) :
            GetMoviesActivityState()
    }
}