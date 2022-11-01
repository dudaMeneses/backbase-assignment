package com.duda.backbaseassignment.infra.database

import com.duda.backbaseassignment.BackbaseAssignmentApplication
import com.duda.backbaseassignment.domain.model.Movie
import com.duda.backbaseassignment.domain.model.MovieRating
import com.duda.backbaseassignment.domain.port.MovieRepository
import com.duda.backbaseassignment.domain.service.param.MovieQueryFilter
import com.duda.backbaseassignment.infra.database.records.Tables.MOVIE
import com.duda.backbaseassignment.infra.database.records.Tables.RATING
import com.duda.backbaseassignment.integration.DatabaseTest
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

private const val NEW_MOVIE = "NewMovieTest"
private const val UPDATE_MOVIE = "UpdateMovieTest"

@Transactional
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackbaseAssignmentApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class MovieRepositoryIT: DatabaseTest() {

    @Autowired
    private lateinit var movieRepository: MovieRepository

    @Autowired
    private lateinit var dslContext: DSLContext

    @Nested
    inner class Upsert {

        @Test
        fun `when movie does not exist then insert it on database`(){
            val movie = Movie(title = NEW_MOVIE, boxOfficeValue = BigDecimal(300).setScale(2))
            movieRepository.upsert(movie)

            val result = getMovie(NEW_MOVIE)

            assertEquals(movie, result)
        }

        @Test
        fun `when movie exists then update it on database`(){
            insertMovie(UPDATE_MOVIE)

            val movie = Movie(title = UPDATE_MOVIE, boxOfficeValue = BigDecimal(500).setScale(2))
            movieRepository.upsert(movie)

            val result = getMovie(UPDATE_MOVIE)

            assertEquals(movie, result)
        }
    }

    @Nested
    inner class FindOrderedByBoxValue {
        @Test
        fun `when no ratings then return empty list`(){
            val result = movieRepository.findOrderedByBoxValue(MovieQueryFilter(0,10))

            assertTrue(result.isEmpty())
        }

        @Test
        fun `when one rating then return list with single movie and that movie has the one rating average`(){
            insertMovie(NEW_MOVIE)
            addRating(NEW_MOVIE, 0, 5)

            val result = movieRepository.findOrderedByBoxValue(MovieQueryFilter(0,10))
            val expectation = MovieRating(title = NEW_MOVIE, averageRating = 5, boxValue = BigDecimal(100).setScale(2))

            assertEquals(expectation, result.firstOrNull())
        }

        @Test
        fun `when multiple ratings to one movie then return list with single movie and average rating`(){
            insertMovie(NEW_MOVIE)

            for (rating in 1..5) {
                addRating(NEW_MOVIE, rating.toLong(), rating.toShort())
            }

            val result = movieRepository.findOrderedByBoxValue(MovieQueryFilter(0,10))
            val expectation = MovieRating(title = NEW_MOVIE, averageRating = 3, boxValue = BigDecimal(100).setScale(2))

            assertEquals(expectation, result.firstOrNull())
        }

        @Test
        fun `when multiple ratings for multiple movies then return top 10 movie with higher ratings and ordered by box value`(){
            for (movieIndex in 1..10) {
                val movieTitle = "$NEW_MOVIE: $movieIndex"
                insertMovie(movieTitle)
                for (rating in 3..5) {
                    addRating(movieTitle, rating.toLong(), rating.toShort())
                }
            }

            for (movieIndex in 11..15) {
                val movieTitle = "$NEW_MOVIE: $movieIndex"
                insertMovie(movieTitle)
                for (rating in 0..3) {
                    addRating(movieTitle, rating.toLong(), rating.toShort())
                }
            }

            val result = movieRepository.findOrderedByBoxValue(MovieQueryFilter(0,10))

            result.forEach {
                assertEquals(4, it.averageRating, "Average of movie '${it.title}' should be 4 but was ${it.averageRating}")
            }
        }
    }

    private fun addRating(movieTitle: String, userId: Long, rating: Short) {
        dslContext.insertInto(RATING)
            .set(RATING.RATING_, rating)
            .set(RATING.USER_ID, userId)
            .set(RATING.MOVIE_ID, dslContext.select(MOVIE.ID).from(MOVIE).where(MOVIE.TITLE.eq(movieTitle)))
            .execute()
    }

    private fun insertMovie(movieTitle: String) {
        dslContext.insertInto(MOVIE)
            .set(MOVIE.TITLE, movieTitle)
            .set(MOVIE.BOX_VALUE, BigDecimal(100))
            .execute()
    }

    private fun getMovie(title: String): Movie? {
        return dslContext.select(MOVIE.TITLE, MOVIE.BOX_VALUE)
            .from(MOVIE)
            .where(MOVIE.TITLE.eq(title))
            .fetch { (title, boxValue) -> Movie(title = title, boxOfficeValue = boxValue) }
            .firstOrNull()
    }

}