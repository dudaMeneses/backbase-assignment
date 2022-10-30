package com.duda.backbaseassignment.infra.network.client

import com.duda.backbaseassignment.infra.network.client.response.OmdbMovieResponse
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.DefaultUriBuilderFactory
import org.springframework.web.util.UriBuilderFactory
import org.springframework.web.util.UriComponentsBuilder

@Component
class OmdbClient(
    private val omdbProperties: OmdbProperties,
    private val restTemplate: RestTemplate,
    private val uriBuilderFactory: UriBuilderFactory = DefaultUriBuilderFactory(omdbProperties.url)
) {

    @CircuitBreaker(name = "default", fallbackMethod = "fallback")
    fun getMovieInformation(movieTitle: String): OmdbMovieResponse? {
        val uri = UriComponentsBuilder.fromUri(uriBuilderFactory.expand("/"))
            .queryParam("apiKey", omdbProperties.token)
            .queryParam("t", movieTitle)
            .queryParam("type", "movie")
            .queryParam("plot", "short")
            .queryParam("r", "json")
            .build().toUri()

        val headers = HttpHeaders().apply {
            accept = listOf(MediaType.APPLICATION_JSON)
        }

        return restTemplate.exchange(uri, HttpMethod.GET, HttpEntity<OmdbMovieResponse>(headers),OmdbMovieResponse::class.java).body
    }

    private fun fallback(movieTitle: String, throwable: Throwable): OmdbMovieResponse? {
        throw OmdbIntegrationException(movieTitle)
    }
}