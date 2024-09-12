package com.example.moviesdbapplication.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviesdbapplication.data.remote.model.ResultResponse
import com.example.moviesdbapplication.utils.Constants.URL.URL_IMAGE

@Composable
fun MovieItem(movie: ResultResponse, onMovieClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onMovieClick(movie.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF7F2F9),
        )
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            AsyncImage(
                model = URL_IMAGE.plus(movie.poster_path),
                contentDescription = movie.title,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = movie.release_date,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}