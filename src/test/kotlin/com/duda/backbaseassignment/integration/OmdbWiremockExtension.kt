package com.duda.backbaseassignment.integration

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

const val NOT_FOUND: String = "MovieNotFound"
const val BAD_REQUEST: String = "MovieBadRequest"
const val TIMEOUT: String = "MovieTimeout"
const val HAPPY_PATH: String = "MovieHappyPath"

private const val OMDB_PORT = 9876

private val omdbWiremockServer = WireMockServer(WireMockConfiguration.options().port(OMDB_PORT))

@TestConfiguration
class OmdbWiremockExtension : BeforeAllCallback, AfterAllCallback {
    val endpoint = "/"

    init {
        omdbWiremockServer.start()
        WireMock.configureFor("localhost", omdbWiremockServer.port())
    }

    override fun beforeAll(context: ExtensionContext?) {
        System.setProperty("omdb.url", "http://localhost:$OMDB_PORT")
        System.setProperty("omdb.token", "12345")

        mockBadRequest()
        mockTimeout()
        mockNotFound()
        mockHappyPath()
    }

    private fun mockHappyPath() {
        omdbWiremockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo(endpoint))
            .withQueryParams(mapOf(
                "apiKey" to WireMock.equalTo("12345"),
                "t" to WireMock.equalTo(HAPPY_PATH),
                "r" to WireMock.equalTo("json"),
                "type" to WireMock.equalTo("movie"),
                "plot" to WireMock.equalTo("short")
            ))
            .willReturn(WireMock.ok(
                """
                    {
                        "Title":"The Lord of the Rings: The Fellowship of the Ring",
                        "Year":"2001",
                        "Rated":"PG-13",
                        "Released":"19 Dec 2001",
                        "Runtime":"178 min",
                        "Genre":"Action, Adventure, Drama",
                        "Director":"Peter Jackson",
                        "Writer":"J.R.R. Tolkien, Fran Walsh, Philippa Boyens",
                        "Actors":"Elijah Wood, Ian McKellen, Orlando Bloom",
                        "Plot":"A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.",
                        "Language":"English, Sindarin",
                        "Country":"New Zealand, United States",
                        "Awards":"Won 4 Oscars. 121 wins & 126 nominations total",
                        "Poster":"https://m.media-amazon.com/images/M/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_SX300.jpg",
                        "Ratings":[
                            {
                                "Source":"Internet Movie Database",
                                "Value":"8.8/10"
                            },
                            {
                                "Source":"Rotten Tomatoes",
                                "Value":"91%"
                            },
                            {
                                "Source":"Metacritic",
                                "Value":"92/100"
                            }
                        ],
                        "Metascore":"92",
                        "imdbRating":"8.8",
                        "imdbVotes":"1,858,615",
                        "imdbID":"tt0120737",
                        "Type":"movie",
                        "DVD":"06 Aug 2002",
                        "BoxOffice":"$316,115,420",
                        "Production":"N/A",
                        "Website":"N/A",
                        "Response":"True"
                    }
                """.trimIndent())
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            ))
    }

    private fun mockNotFound() {
        omdbWiremockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo(endpoint))
            .withQueryParams(mapOf(
                "apiKey" to WireMock.equalTo("12345"),
                "t" to WireMock.equalTo(NOT_FOUND),
                "r" to WireMock.equalTo("json"),
                "type" to WireMock.equalTo("movie"),
                "plot" to WireMock.equalTo("short")))
            .willReturn(WireMock.notFound()))
    }

    private fun mockTimeout() {
        omdbWiremockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo(endpoint))
            .withQueryParams(mapOf(
                "apiKey" to WireMock.equalTo("12345"),
                "t" to WireMock.equalTo(TIMEOUT),
                "r" to WireMock.equalTo("json"),
                "type" to WireMock.equalTo("movie"),
                "plot" to WireMock.equalTo("short")))
            .willReturn(WireMock.ok().withFixedDelay(10000)))
    }

    private fun mockBadRequest() {
        omdbWiremockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo(endpoint))
            .withQueryParams(mapOf(
                "apiKey" to WireMock.equalTo("12345"),
                "t" to WireMock.equalTo(BAD_REQUEST),
                "r" to WireMock.equalTo("json"),
                "type" to WireMock.equalTo("movie"),
                "plot" to WireMock.equalTo("short")))
            .willReturn(WireMock.badRequest()))
    }

    override fun afterAll(context: ExtensionContext?) {
        omdbWiremockServer.stop()
    }

}
