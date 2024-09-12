package com.example.moviesdbapplication.presentation.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesdbapplication.presentation.ui.components.MovieDetailsScreen
import com.example.moviesdbapplication.presentation.ui.components.MovieListScreen


@Composable
fun MovieApp(movieViewModel: MovieViewModel = hiltViewModel()) {
    val selectedTabIndex by movieViewModel.selectedTabIndex.collectAsState()
    val navController = rememberNavController()

    Scaffold(
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "movieList",
            modifier = Modifier.padding(padding)
        ) {
            composable("movieList") {
                MovieListScreen(
                    selectedTabIndex = selectedTabIndex,
                    movieViewModel = movieViewModel,
                    navController = navController
                )
            }
            composable(
                route = "movieDetails/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
                MovieDetailsScreen(
                    movieViewModel = movieViewModel,
                    movieId = movieId,
                    navController
                )
            }
        }
    }
}



