package com.duda.backbaseassignment.application.config.security

import com.duda.backbaseassignment.application.exception.MissingTokenHeaderException
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class JwtValidationFilter(private val jwtService: JwtService): GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        request as HttpServletRequest

        val tokenHeader = request.getHeader("token") ?: throw MissingTokenHeaderException(request)

        val mutableRequest = MutableHttpRequest(request)
        mutableRequest.putHeader("userId", jwtService.getUserId(tokenHeader).toString())
        chain?.doFilter(mutableRequest, response)
    }

}