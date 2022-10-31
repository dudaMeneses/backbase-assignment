package com.duda.backbaseassignment.domain.service

import com.duda.backbaseassignment.domain.model.Movie
import com.duda.backbaseassignment.domain.port.MovieInfoProvider
import com.duda.backbaseassignment.domain.port.MovieRepository
import com.duda.backbaseassignment.domain.port.RatingRepository
import com.duda.backbaseassignment.domain.service.exception.MovieInfoNotFoundException
import com.duda.backbaseassignment.domain.service.exception.UserAlreadyRatedMovieException
import com.duda.backbaseassignment.domain.service.param.MovieRatingParam
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class RateMovieCommandHandlerTest {

    @InjectMockKs
    private lateinit var handler: RateMovieCommandHandler

    @MockK(relaxUnitFun = true)
    private lateinit var movieRepository: MovieRepository;

    @MockK(relaxUnitFun = true)
    private lateinit var ratingRepository: RatingRepository

    @MockK
    private lateinit var movieInfoProvider: MovieInfoProvider

    @BeforeEach
    fun init() = MockKAnnotations.init(this)

    @Test
    fun `when movie info not found on OMDb API then throw exception`(){
        every { movieInfoProvider.getMovieInfo(any()) } returns null

        assertThrows(MovieInfoNotFoundException::class.java) { handler.handle(MovieRatingParam(1, "TestMovie", 2)) }
    }

    @Test
    fun `when rating already exists then throw exception`(){
        every { movieInfoProvider.getMovieInfo(any()) } returns Movie(title = "MovieTest", boxOfficeValue = BigDecimal(12345))
        every { ratingRepository.exists(any(), any()) } returns true

        assertThrows(UserAlreadyRatedMovieException::class.java) { handler.handle(MovieRatingParam(1, "TestMovie", 2)) }
    }

    @Test
    fun `when rating does not exist then save rating`(){
        every { movieInfoProvider.getMovieInfo(any()) } returns Movie(title = "MovieTest", boxOfficeValue = BigDecimal(12345))
        every { ratingRepository.exists(any(), any()) } returns false

        handler.handle(MovieRatingParam(1, "TestMovie", 2))

        verify(exactly = 1) { ratingRepository.add(any()) }
    }
}