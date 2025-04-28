package com.lotus.moviesapp.data.remote.dto

/**
 * Data class representing the genre response from the API
 * The API returns a list of lists where each inner list contains:
 * - First element (index 0): Genre name as String
 * - Second element (index 1): Count of movies in that genre as Int
 */
data class GenreResponse(
    val genres: List<List<Any>>
) {
    /**
     * Convert the raw API response format to a more usable format
     * @return List of Pair<String, Int> where each pair represents (genre name, movie count)
     */
    fun toGenrePairs(): List<Pair<String, Int>> {
        return genres.map { genreData ->
            val name = genreData[0] as String
            val count = (genreData[1] as Number).toInt()
            Pair(name, count)
        }
    }
}