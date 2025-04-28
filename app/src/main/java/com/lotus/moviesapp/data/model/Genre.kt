package com.lotus.moviesapp.data.model

data class Genre(
    val name: String,
    val count: Int,
    val isSelected: Boolean = false
)