package com.duda.backbaseassignment.domain.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class MovieInfoNotFoundException(movieTitle: String) :
    ResponseStatusException(HttpStatus.NOT_FOUND, "Movie information $movieTitle not found on OMDb")
