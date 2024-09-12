package com.example.moviesdbapplication.data.remote.model

data class MovieResponse(
    val page:Int,
    val total_pages:Int,
    val total_results:Int,
    val results:List<ResultResponse>
)
