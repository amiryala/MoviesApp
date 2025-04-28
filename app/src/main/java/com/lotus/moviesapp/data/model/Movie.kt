package com.lotus.moviesapp.data.model

data class Movie(
    val id: String,
    val title: String,
    val releaseYear: String, // Extracted from release_date
    val overview: String,
    val genres: List<String>,
    val url: String
)