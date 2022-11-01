package com.duda.backbaseassignment.infra.adapter

import com.duda.backbaseassignment.domain.model.Movie
import com.duda.backbaseassignment.infra.network.client.OmdbClient
import com.duda.backbaseassignment.infra.network.client.response.OmdbMovieResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class MovieInfoProviderTest {

    @InjectMockKs
    private lateinit var movieInfoProviderAdapter: MovieInfoProviderAdapter

    @MockK
    private lateinit var omdbClient: OmdbClient

    @BeforeEach
    fun init() = MockKAnnotations.init(this)

    @Test
    fun `when integration returns null then returns null`(){
        every { omdbClient.getMovieInformation(any()) } returns null
        assertNull(movieInfoProviderAdapter.getMovieInfo("Test"))
    }

    @Test
    fun `when happy path then return movie object`(){
        every { omdbClient.getMovieInformation(any()) } returns OmdbMovieResponse("Test", BigDecimal(100))

        val expectation = Movie(title = "Test", boxOfficeValue = BigDecimal(100))

        assertEquals(expectation, movieInfoProviderAdapter.getMovieInfo("Test"))
    }
}