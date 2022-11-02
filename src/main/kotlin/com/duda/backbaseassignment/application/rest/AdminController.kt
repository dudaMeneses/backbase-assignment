package com.duda.backbaseassignment.application.rest

import com.duda.backbaseassignment.application.config.security.JwtService
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@Profile(value = ["!pro"])
class AdminController(private val jwtService: JwtService) {

    @GetMapping(path = ["/token/{userId}"], produces = [APPLICATION_JSON_VALUE])
    fun getToken(@PathVariable userId: Long): String = jwtService.createToken(userId)
}