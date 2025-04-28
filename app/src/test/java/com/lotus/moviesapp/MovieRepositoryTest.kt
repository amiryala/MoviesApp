package com.lotus.moviesapp

import com.lotus.moviesapp.data.remote.MovieApi
import com.lotus.moviesapp.data.repository.MovieRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    @Mock
    private lateinit var movieApi: MovieApi

    private lateinit var repository: MovieRepository

    @Before
    fun setup() {
        repository = MovieRepository(movieApi)
    }

    @Test
    fun `getGenres should map API response correctly`() = runBlocking {
        // Given
        val mockResponse = listOf(listOf("Action", 100), listOf("Comedy", 200))
        `when`(movieApi.getGenres()).thenReturn(mockResponse)

        // When
        val result = repository.getGenres().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Action", result[0].name)
        assertEquals(100, result[0].count)
        assertEquals("Comedy", result[1].name)
        assertEquals(200, result[1].count)
    }
}