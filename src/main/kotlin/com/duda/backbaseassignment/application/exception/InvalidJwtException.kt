package com.duda.backbaseassignment.application.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidJwtException() : ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token")
