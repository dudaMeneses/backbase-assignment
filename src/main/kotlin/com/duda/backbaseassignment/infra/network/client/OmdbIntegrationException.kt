package com.duda.backbaseassignment.infra.network.client

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class OmdbIntegrationException(movieTitle: String, throwable: Throwable) :
    ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem integrating with OMDb API for movie title $movieTitle", throwable)
