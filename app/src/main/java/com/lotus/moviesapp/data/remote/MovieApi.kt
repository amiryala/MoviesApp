package com.lotus.moviesapp.data.remote

import com.lotus.moviesapp.data.remote.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("api/genres")
    suspend fun getGenres(): List<List<Any>>

    @GET("api/movies")
    suspend fun getMovies(
        @Query("limit") limit: Int = 20,
        @Query("from") from: Int = 0,
        @Query("genre") genre: String? = null
    ): List<MovieResponse>
}