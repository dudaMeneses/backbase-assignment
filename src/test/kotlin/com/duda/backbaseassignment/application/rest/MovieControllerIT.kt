package com.duda.backbaseassignment.application.rest

import com.duda.backbaseassignment.BackbaseAssignmentApplication
import com.duda.backbaseassignment.application.config.security.JwtService
import com.duda.backbaseassignment.application.rest.model.response.NominationResponse
import com.duda.backbaseassignment.domain.model.valueObject.Category
import com.duda.backbaseassignment.domain.service.MovieQueryHandler
import com.duda.backbaseassignment.domain.service.OscarNominationQueryHandler
import com.duda.backbaseassignment.domain.service.RateMovieCommandHandler
import com.duda.backbaseassignment.domain.service.exception.MovieInfoNotFoundException
import com.duda.backbaseassignment.domain.service.exception.OscarNomineeNotFoundException
import com.duda.backbaseassignment.domain.service.exception.UserAlreadyRatedMovieException
import com.duda.backbaseassignment.domain.service.param.MovieQueryFilter
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackbaseAssignmentApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class MovieControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var oscarNominationQueryHandler: OscarNominationQueryHandler

    @MockkBean(relaxUnitFun = true)
    private lateinit var rateMovieCommandHandler: RateMovieCommandHandler

    @MockkBean
    private lateinit var movieQueryHandler: MovieQueryHandler

    @Autowired
    private lateinit var jwtService: JwtService

    private lateinit var token: String

    @BeforeEach
    fun init(){
        token = jwtService.createToken(0)
    }

    @Nested
    @DisplayName("Oscar Nominations API")
    inner class OscarNominationsAPI {
        @Test
        fun `when movie not found then return NOT FOUND (404) status`() {
            every { oscarNominationQueryHandler.find(any()) } throws OscarNomineeNotFoundException("TestNotFound")

            val result = mockMvc.perform(MockMvcRequestBuilders.get(
                "/movies/oscar-nominations?movieTitle={movieTitle}&category={category}",
                "TestNotFound", "BEST_PICTURE")
                .header("token", token))
                .andExpect(status().isNotFound)
                .andReturn()

            assertEquals("Movie TestNotFound was not nominee to Oscar until 2010", result.response.errorMessage)
        }

        @Test
        fun `when movie had a nomination then return OK (200) status and nomination information`(){
            every { oscarNominationQueryHandler.find(any()) } returns NominationResponse(nominationYear = 2020, category = Category.BEST_PICTURE, true)

            mockMvc.perform(MockMvcRequestBuilders.get(
                "/movies/oscar-nominations?movieTitle={movieTitle}&category={category}",
                "HappyPathTest", "BEST_PICTURE")
                .header("token", token))
                .andExpect(status().isOk)
                .andExpect(content().json("""{"nominationYear":2020,"category":"BEST_PICTURE","winner":true}"""))
        }
    }

    @Nested
    @DisplayName("Add Rating API")
    inner class AddRatingAPI {

        @Test
        fun `when rating is above 5 then return BAD REQUEST (400) status`(){
            mockMvc.perform(MockMvcRequestBuilders.post("/movies/ratings")
                .header("token", token)
                .content(json("AboveRangeTest", 10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
        }

        @Test
        fun `when rating is below 0 then return BAD REQUEST (400) status`(){
            mockMvc.perform(MockMvcRequestBuilders.post("/movies/ratings")
                .header("token", token)
                .content(json("AboveRangeTest", -1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
        }

        @Test
        fun `when movie info not found then return NOT FOUND (404) status`(){
            every { rateMovieCommandHandler.handle(any()) } throws MovieInfoNotFoundException("NotFoundTest")

            val result = mockMvc.perform(MockMvcRequestBuilders.post("/movies/ratings")
                .header("token", token)
                .content(json("NotFoundTest"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andReturn()

            assertEquals("Movie information NotFoundTest not found on OMDb", result.response.errorMessage)
        }

        @Test
        fun `when rating already exists then return BAD REQUEST (400) status`(){
            every { rateMovieCommandHandler.handle(any()) } throws UserAlreadyRatedMovieException("BadRequestTest", 0)

            val result = mockMvc.perform(MockMvcRequestBuilders.post("/movies/ratings")
                .header("token", token)
                .content(json("BadRequestTest"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andReturn()

            assertEquals("Movie BadRequestTest was already rated by user 0", result.response.errorMessage)
        }

        @Test
        fun `when happy path then return CREATED (201) status`(){
            mockMvc.perform(MockMvcRequestBuilders.post("/movies/ratings")
                .header("token", token)
                .content(json("HappyPath"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated)
        }

        private fun json(movieTitle: String, rating: Int = 2): String = """{"rating":$rating,"movieTitle":"$movieTitle"}"""
    }

    @Nested
    @DisplayName("Find Movies API")
    inner class FindMovies {

        @Test
        fun `when no query param is informed then defaults are used`(){
            val filterCaptor = slot<MovieQueryFilter>()

            every { movieQueryHandler.find(capture(filterCaptor)) } returns emptyList()

            mockMvc.perform(MockMvcRequestBuilders.get("/movies/ratings")
                .header("token", token))
                .andExpect(status().isOk)

            assertEquals(MovieQueryFilter(0, 10), filterCaptor.captured)
        }

        @Test
        fun `when page size is ONE MILLION then returns BAD REQUEST (400)`(){
            mockMvc.perform(MockMvcRequestBuilders.get("/movies/ratings?size=1000000")
                .header("token", token))
                .andExpect(status().isBadRequest)
        }
    }

}