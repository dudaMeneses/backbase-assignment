package com.duda.backbaseassignment.infra.database

import com.duda.backbaseassignment.BackbaseAssignmentApplication
import com.duda.backbaseassignment.domain.model.Rating
import com.duda.backbaseassignment.domain.port.RatingRepository
import com.duda.backbaseassignment.infra.database.records.Tables.MOVIE
import com.duda.backbaseassignment.infra.database.records.Tables.RATING
import com.duda.backbaseassignment.infra.database.records.tables.records.RatingRecord
import com.duda.backbaseassignment.integration.IntegrationTest
import org.jooq.DSLContext
import org.jooq.exception.DataAccessException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Transactional
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackbaseAssignmentApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class RatingRepositoryIT: IntegrationTest() {

    @Autowired
    private lateinit var ratingRepository: RatingRepository

    @Autowired
    private lateinit var dslContext: DSLContext

    @Nested
    @DisplayName("Add Rating")
    inner class Add {
        @Test
        fun `when movie exists then rating is persisted`(){
            saveMovie("TestMovie")

            ratingRepository.add(Rating(2, 1, "TestMovie"))

            val result = getRating("TestMovie", 1)

            assertNotNull(result)
            assertEquals(2, result!!.rating)
        }

        @Test
        fun `when user try to rate twice same movie then throw exception`(){
            saveMovie("TestMovie")

            ratingRepository.add(Rating(3, 1, "TestMovie"))

            assertThrows(DataAccessException::class.java) { ratingRepository.add(Rating(2, 1, "TestMovie")) }
        }

        @Test
        fun `when movie doesn't exist then throw exception`(){
            assertThrows(DataAccessException::class.java) { ratingRepository.add(Rating(2, 1, "TestMovie")) }
        }
    }

    @Nested
    @DisplayName("Exists Rating")
    inner class Exists {
        @Test
        fun `when rating does not exist the return false`(){
            saveMovie("TestMovie")

            assertFalse(ratingRepository.exists("TestMovie", 1))
        }

        @Test
        fun `when rating exists for user the return true`(){
            saveMovie("TestMovie")
            ratingRepository.add(Rating(3, 1, "TestMovie"))

            assertTrue(ratingRepository.exists("TestMovie", 1))
        }

        @Test
        fun `when rating exists for another user the return false`(){
            saveMovie("TestMovie")
            ratingRepository.add(Rating(3, 2, "TestMovie"))

            assertFalse(ratingRepository.exists("TestMovie", 1))
        }

        @Test
        fun `when movie does not exist the return false`(){
            assertFalse(ratingRepository.exists("TestMovie", 1))
        }
    }

    fun saveMovie(title: String) {
        dslContext.insertInto(MOVIE, MOVIE.TITLE, MOVIE.BOX_VALUE)
            .values(title, BigDecimal(123))
            .execute()
    }

    fun getRating(title: String, user: Long): RatingRecord? {
        return dslContext.select()
            .from(RATING)
            .where(RATING.MOVIE_ID.eq(dslContext.select(MOVIE.ID).from(MOVIE).where(MOVIE.TITLE.eq(title))))
            .and(RATING.USER_ID.eq(user))
            .fetchInto(RatingRecord::class.java)
            .firstOrNull()
    }
}