package com.lotus.moviesapp.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.lotus.moviesapp.data.model.Movie
import com.lotus.moviesapp.ui.MainViewModel
import androidx.core.net.toUri

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val genres by viewModel.genres.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()
    val moviesFlow = viewModel.movies.collectAsLazyPagingItems()
    val context = LocalContext.current

    Column(
        modifier = Modifier.statusBarsPadding() // Add padding for status bar
    ) {
        Text(
            text = "Movies App",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        GenreSelector(
            genres = genres,
            selectedGenre = selectedGenre,
            onGenreSelected = { viewModel.selectGenre(it) }
        )

        MoviesContent(
            movies = moviesFlow,
            onMovieClick = { movie ->
                val intent = Intent(Intent.ACTION_VIEW, movie.url.toUri())
                context.startActivity(intent)
            }
        )
    }
}

@Composable
fun MoviesContent(
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    LazyColumn {
        items(
            count = movies.itemCount,
            key = { index ->
                val movie = movies[index]
                movie?.id ?: index.toString()
            }
        ) { index ->
            val movie = movies[index]
            if (movie != null) {
                MovieItem(
                    movie = movie,
                    onClick = { onMovieClick(movie) }
                )
            }
        }

        when {
            movies.loadState.refresh is LoadState.Loading -> {
                item { LoadingItem() }
            }
            movies.loadState.append is LoadState.Loading -> {
                item { LoadingItem() }
            }
            movies.loadState.refresh is LoadState.Error -> {
                val error = movies.loadState.refresh as LoadState.Error
                item { ErrorItem(error.error.message ?: "Unknown error") }
            }
            movies.loadState.append is LoadState.Error -> {
                val error = movies.loadState.append as LoadState.Error
                item { ErrorItem(error.error.message ?: "Unknown error") }
            }
        }
    }
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ErrorItem(message: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.error
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onError,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}