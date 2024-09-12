package com.example.moviesdbapplication.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesdbapplication.presentation.ui.components.MovieDetailsScreen
import com.example.moviesdbapplication.presentation.ui.components.MovieListScreen
import com.example.moviesdbapplication.utils.Constants.Movies.TAB_MOVIES_NOW_PLAYING
import com.example.moviesdbapplication.utils.Constants.Movies.TAB_MOVIES_POPULAR
import com.example.moviesdbapplication.utils.Constants.Movies.TAB_MOVIES_UPCOMING


@Composable
fun MovieApp(movieViewModel: MovieViewModel = hiltViewModel()) {
    val selectedTabIndex by movieViewModel.selectedTabIndex.collectAsState()

    val tabTitles = listOf(
        TAB_MOVIES_UPCOMING, TAB_MOVIES_POPULAR, TAB_MOVIES_NOW_PLAYING
    )
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(selected = selectedTabIndex == index,
                        onClick = { movieViewModel.onTabSelected(index) },
                        text = { Text(text = title) })
                }
            }
        }
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
                MovieDetailsScreen(movieViewModel = movieViewModel, movieId = movieId)
            }
        }
    }

}




