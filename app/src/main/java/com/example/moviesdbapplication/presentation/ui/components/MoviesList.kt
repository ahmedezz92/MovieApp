package com.example.moviesdbapplication.presentation.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviesdbapplication.R
import com.example.moviesdbapplication.data.remote.model.ResultResponse
import com.example.moviesdbapplication.utils.connectivity.NetworkUtil

@Composable
fun MovieList(movies: List<ResultResponse>, onMovieClick: (Int) -> Unit) {
    LazyColumn(modifier = Modifier.padding(top = 60.dp)) {
        items(movies) { movie ->
            MovieItem(movie, onMovieClick = onMovieClick)
        }
    }
}

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }

}


@Composable
fun ErrorState(message: String) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (NetworkUtil.isInternetAvailable(context).not())
            Toast.makeText(
                context,
                stringResource(id = R.string.label_no_internet_connection),
                Toast.LENGTH_SHORT
            ).show()
        else
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}