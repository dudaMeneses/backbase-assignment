package com.duda.backbaseassignment.infra.network.client

import com.duda.backbaseassignment.infra.network.client.response.OmdbMovieResponse
import com.duda.backbaseassignment.integration.*
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@ActiveProfiles(value = ["integration-test"])
@ExtendWith(OmdbWiremockExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class OmdbClientIT {

    @Autowired
    lateinit var omdbClient: OmdbClient

    @Autowired
    lateinit var cbr: CircuitBreakerRegistry

    @BeforeEach
    fun init() = cbr.allCircuitBreakers
        .forEach { if (it.state == CircuitBreaker.State.OPEN) it.transitionToHalfOpenState() }

    @Test
    fun `when not found then throw fallback exception`(){
        assertThrows(OmdbIntegrationException::class.java) { omdbClient.getMovieInformation(NOT_FOUND) }
    }

    @Test
    fun `when bad request then throw fallback exception`(){
        assertThrows(OmdbIntegrationException::class.java) { omdbClient.getMovieInformation(BAD_REQUEST) }
    }

    @Test
    fun `when timeout then throw fallback exception`(){
        assertThrows(OmdbIntegrationException::class.java) { omdbClient.getMovieInformation(TIMEOUT) }
    }

    @Test
    fun `when happy path then return result`(){
        assertEquals(
            OmdbMovieResponse("The Lord of the Rings: The Fellowship of the Ring", BigDecimal(316115420)),
            omdbClient.getMovieInformation(HAPPY_PATH)
        )
    }
}