package com.example.moviesdbapplication.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.moviesdbapplication.R
import com.example.moviesdbapplication.presentation.ui.screens.MovieApp
import com.example.moviesdbapplication.utils.connectivity.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MovieApp()
            }
        }
    }

}