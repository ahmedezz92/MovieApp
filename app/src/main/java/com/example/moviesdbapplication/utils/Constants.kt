package com.example.moviesdbapplication.utils

object Constants {
    object Movies {
        const val TAB_MOVIES_UPCOMING = "Upcoming"
        const val TAB_MOVIES_POPULAR = "Popular"
        const val TAB_MOVIES_NOW_PLAYING = "Now Playing"
    }

    object URL {
        const val URL_BASE = "https://api.themoviedb.org/3/"
        const val URL_IMAGE = "https://image.tmdb.org/t/p/w500/"
    }

    object Authorization {
        const val TOKEN_BEARER =
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2YWJlODMxNTNmYWRiYjk3ZWE3ZjIyMWZjYjM3NmE4YyIsIm5iZiI6MTcyNjEyNDQ0OS41MDU0MDgsInN1YiI6IjY2ZDljODQ0NmYwNGQ2Nzc5OWJmNzNhOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.67avmWu5BuqnQbW65Y5SwoyGCwXd9OCzWZ5T3pIbBwE"

    }
}