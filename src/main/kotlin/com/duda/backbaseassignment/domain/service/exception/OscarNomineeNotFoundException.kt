package com.duda.backbaseassignment.domain.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class OscarNomineeNotFoundException(movieTitle: String) :
    ResponseStatusException(HttpStatus.NOT_FOUND, "Movie $movieTitle was not nominee to Oscar until 2010")
