package com.example.moviesdbapplication.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviesdbapplication.data.remote.model.ResultResponse

@Composable
fun MovieList(movies: List<ResultResponse>, onMovieClick: (Int) -> Unit) {
    LazyColumn(modifier = Modifier.padding(top = 60.dp)) {
        items(movies) { movie ->
            MovieItem(movie, onMovieClick = onMovieClick)
        }
    }
}