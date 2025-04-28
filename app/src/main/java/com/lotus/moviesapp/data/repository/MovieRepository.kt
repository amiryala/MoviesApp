package com.lotus.moviesapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lotus.moviesapp.data.model.Genre
import com.lotus.moviesapp.data.model.Movie
import com.lotus.moviesapp.data.remote.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
) {
    fun getGenres(): Flow<List<Genre>> = flow {
        try {
            val genreResponse = movieApi.getGenres()
            val genres = genreResponse.map {
                Genre(
                    name = it[0] as String,
                    count = (it[1] as Number).toInt()
                )
            }
            emit(genres)
        } catch (e: Exception) {
            // Handle error
        }
    }

    fun getMoviesPagingSource(genre: String?): PagingSource<Int, Movie> {
        return MoviePagingSource(movieApi, genre)
    }
}

class MoviePagingSource(
    private val movieApi: MovieApi,
    private val genre: String?
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val position = params.key ?: 0
            val response = movieApi.getMovies(
                limit = params.loadSize,
                from = position,
                genre = genre
            )

            val movies = response.map {
                Movie(
                    id = it.id,
                    title = it.title,
                    releaseYear = it.release_date.split("-")[0], // Extract year
                    overview = it.overview,
                    genres = it.genres,
                    url = it.url
                )
            }

            return LoadResult.Page(
                data = movies,
                prevKey = if (position == 0) null else position - params.loadSize,
                nextKey = if (movies.isEmpty()) null else position + params.loadSize
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(state.config.pageSize)
        }
    }
}