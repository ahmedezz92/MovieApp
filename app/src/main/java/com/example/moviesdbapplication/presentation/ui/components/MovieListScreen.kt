package com.example.moviesdbapplication.presentation.ui.components

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.moviesdbapplication.presentation.ui.screens.MovieViewModel
import com.example.moviesdbapplication.utils.Constants

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
    val isError by movieViewModel.errorCode.collectAsState()

    val tabTitles = listOf(
        Constants.Movies.TAB_MOVIES_UPCOMING,
        Constants.Movies.TAB_MOVIES_POPULAR,
        Constants.Movies.TAB_MOVIES_NOW_PLAYING
    )

    when (selectedTabIndex) {
        0 -> {
            LaunchedEffect(Unit) {
                if (movieViewModel.isLoadedUpcoming.not()) {
                    movieViewModel.getUpcomingMovies().collect { state ->
                        movieViewModel.handleStateUpcomingMovies(state)
                    }
                }
            }
            MovieList(movies = upcomingMovies, onMovieClick = { movie ->
                navController.navigate("movieDetails/$movie")
            })
        }

        1 -> {
            if (movieViewModel.isLoadedPopular.not()) {
                LaunchedEffect(Unit) {
                    movieViewModel.getPopularMovies().collect { state ->
                        movieViewModel.handleStatePopularMovies(state)
                    }
                }
            }
            MovieList(movies = popularMovies, onMovieClick = { movie ->
                navController.navigate("movieDetails/$movie")
            })
        }

        2 -> {
            LaunchedEffect(Unit) {
                if (movieViewModel.isLoadedNowPlaying.not()) {
                    movieViewModel.getNowPlayingMovies().collect { state ->
                        movieViewModel.handleStateNowPlayingMovies(state)
                    }
                }
            }
            MovieList(movies = nowPlayingMovies, onMovieClick = { movie ->
                navController.navigate("movieDetails/$movie")
            })
        }
    }
    isLoading.takeIf { it }?.let {
        LoadingState()
    }
    isError.takeIf { it != 200 }?.let {
        ErrorState()
    }


    TabRow(selectedTabIndex = selectedTabIndex) {
        tabTitles.forEachIndexed { index, title ->
            Tab(selected = selectedTabIndex == index,
                onClick = { movieViewModel.onTabSelected(index) },
                text = { Text(text = title) })
        }
    }

}