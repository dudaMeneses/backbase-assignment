package com.duda.backbaseassignment.application.rest

import com.duda.backbaseassignment.BackbaseAssignmentApplication
import com.duda.backbaseassignment.application.rest.model.response.NominationResponse
import com.duda.backbaseassignment.domain.model.valueObject.Category
import com.duda.backbaseassignment.domain.service.OscarNominationQueryHandler
import com.duda.backbaseassignment.domain.service.RateMovieCommandHandler
import com.duda.backbaseassignment.domain.service.exception.MovieInfoNotFoundException
import com.duda.backbaseassignment.domain.service.exception.OscarNomineeNotFoundException
import com.duda.backbaseassignment.domain.service.exception.UserAlreadyRatedMovieException
import com.duda.backbaseassignment.domain.service.param.MovieRatingParam
import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Transactional
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackbaseAssignmentApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class MovieControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var oscarNominationQueryHandler: OscarNominationQueryHandler

    @MockBean
    private lateinit var rateMovieCommandHandler: RateMovieCommandHandler

    @Nested
    @DisplayName("Oscar Nominations API")
    inner class OscarNominationsAPI {
        @Test
        fun `when movie not found then return NOT FOUND (404) status`() {
            `when`(oscarNominationQueryHandler.find(OscarNominationFilter("TestNotFound", Category.BEST_PICTURE))).thenThrow(OscarNomineeNotFoundException("TestNotFound"))

            val result = mockMvc.perform(MockMvcRequestBuilders.get(
                "/movies/oscar-nominations?movieTitle={movieTitle}&category={category}",
                "TestNotFound", "BEST_PICTURE"))
                .andExpect(status().isNotFound)
                .andReturn()

            assertEquals("Movie TestNotFound was not nominee to Oscar until 2010", result.response.errorMessage)
        }

        @Test
        fun `when movie had a nomination then return OK (200) status and nomination information`(){
            `when`(oscarNominationQueryHandler.find(OscarNominationFilter("HappyPathTest", Category.BEST_PICTURE)))
                .thenReturn(NominationResponse(nominationYear = 2020, category = Category.BEST_PICTURE, true))

            mockMvc.perform(MockMvcRequestBuilders.get(
                "/movies/oscar-nominations?movieTitle={movieTitle}&category={category}",
                "HappyPathTest", "BEST_PICTURE"))
                .andExpect(status().isOk)
                .andExpect(content().json("""{"nominationYear":2020,"category":"BEST_PICTURE","winner":true}"""))
        }
    }

    @Nested
    @DisplayName("Add Rating API")
    inner class AddRatingAPI {
        @Test
        fun `when movie info not found then return NOT FOUND (404) status`(){
            `when`(rateMovieCommandHandler.handle(MovieRatingParam(0, "NotFoundTest", 2))).thenThrow(MovieInfoNotFoundException("NotFoundTest"))

            val result = mockMvc.perform(MockMvcRequestBuilders.post("/movies/ratings")
                .content(json("NotFoundTest"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andReturn()

            assertEquals("Movie information NotFoundTest not found on OMDb", result.response.errorMessage)
        }

        @Test
        fun `when rating already exists then return BAD REQUEST (400) status`(){
            `when`(rateMovieCommandHandler.handle(MovieRatingParam(0, "BadRequestTest", 2))).thenThrow(UserAlreadyRatedMovieException("BadRequestTest", 0))

            val result = mockMvc.perform(MockMvcRequestBuilders.post("/movies/ratings")
                .content(json("BadRequestTest"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andReturn()

            assertEquals("Movie BadRequestTest was already rated by user 0", result.response.errorMessage)
        }

        @Test
        fun `when happy path then return CREATED (201) status`(){
            mockMvc.perform(MockMvcRequestBuilders.post("/movies/ratings")
                .content(json("HappyPath"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated)
        }

        private fun json(movieTitle: String): String = """{"rating":2,"movieTitle":"$movieTitle"}"""
    }
}