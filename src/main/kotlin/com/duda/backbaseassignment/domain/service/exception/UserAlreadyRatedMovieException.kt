package com.duda.backbaseassignment.domain.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class UserAlreadyRatedMovieException(movieTitle: String, userId: Int) :
    ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie $movieTitle was already rated by user $userId")
