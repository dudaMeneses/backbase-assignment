package com.duda.backbaseassignment.domain.service

import com.duda.backbaseassignment.domain.model.MovieRating
import com.duda.backbaseassignment.domain.port.MovieRepository
import com.duda.backbaseassignment.domain.service.param.MovieQueryFilter
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal
import java.util.stream.IntStream

internal class MovieQueryHandlerTest {

    @InjectMockKs
    private lateinit var movieQueryHandler: MovieQueryHandler

    @MockK
    private lateinit var movieRepository: MovieRepository

    @BeforeEach
    fun init() = MockKAnnotations.init(this)

    @Test
    fun `when empty list returned from DB then return empty list`(){
        every { movieRepository.findOrderedByBoxValue(any()) } returns emptyList()

        assertTrue(movieQueryHandler.find(MovieQueryFilter(0, 10)).isEmpty())
    }

    @ValueSource(ints = [1, 3, 5, 10])
    @ParameterizedTest(name = "when DB retrieves {0} rows then return {0} ratings")
    fun `returns same quantity that DB results`(quantity: Int) {
        every { movieRepository.findOrderedByBoxValue(any()) } returns IntStream.range(1, quantity + 1)
                    .mapToObj {
                        MovieRating("Movie Test: $it", BigDecimal(100), 3)
                    }
                    .toList()

        assertEquals(quantity, movieQueryHandler.find(MovieQueryFilter(0, 10)).size)
    }
}