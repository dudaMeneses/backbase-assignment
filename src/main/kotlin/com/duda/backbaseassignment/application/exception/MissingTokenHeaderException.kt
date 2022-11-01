package com.duda.backbaseassignment.application.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class MissingTokenHeaderException : ResponseStatusException(HttpStatus.BAD_REQUEST, "'token' header is required")
