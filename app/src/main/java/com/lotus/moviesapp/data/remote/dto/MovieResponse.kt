package com.lotus.moviesapp.data.remote.dto

data class MovieResponse(
    val id: String,
    val title: String,
    val release_date: String,
    val overview: String,
    val genres: List<String>,
    val url: String
)