package com.example.moviesdbapplication.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviesdbapplication.presentation.ui.screens.MovieViewModel

@Composable
fun MovieListScreen(
    selectedTabIndex: Int,
    movieViewModel: MovieViewModel,
    navController: NavHostController
) {
    val upcomingMovies by movieViewModel.upcomingMoviesList.collectAsState()
    val popularMovies by movieViewModel.popularMoviesList.collectAsState()
    val nowPlayingMovies by movieViewModel.nowPlayingMoviesList.collectAsState()
    val isLoading by movieViewModel.isLoading.collectAsState()
    when (selectedTabIndex) {
        0 -> {
            LaunchedEffect(Unit) {
                movieViewModel.getUpcomingMovies().collect { state ->
                    movieViewModel.handleStateUpcomingMovies(state)
                }
            }
            MovieList(movies = upcomingMovies, onMovieClick = { movie ->
                navController.navigate("movieDetails/$movie")
            })
        }

        1 -> {
            LaunchedEffect(Unit) {
                movieViewModel.getPopularMovies().collect { state ->
                    movieViewModel.handleStatePopularMovies(state)
                }
            }
            MovieList(movies = popularMovies, onMovieClick = { movie ->
                navController.navigate("movieDetails/$movie")
            })
        }

        2 -> {
            LaunchedEffect(Unit) {
                movieViewModel.getNowPlayingMovies().collect { state ->
                    movieViewModel.handleStateNowPlayingMovies(state)
                }
            }
            MovieList(movies = nowPlayingMovies, onMovieClick = { movie ->
                navController.navigate("movieDetails/$movie")
            })
        }
    }
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.padding(66.dp)
        )
    }

}