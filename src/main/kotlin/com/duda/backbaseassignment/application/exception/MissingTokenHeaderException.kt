package com.duda.backbaseassignment.application.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest

class MissingTokenHeaderException(request: HttpServletRequest) : ResponseStatusException(HttpStatus.BAD_REQUEST, "'token' header is required. ${request.requestURI}")
