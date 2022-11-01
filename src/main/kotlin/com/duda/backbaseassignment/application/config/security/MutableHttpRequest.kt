package com.duda.backbaseassignment.application.config.security

import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper


class MutableHttpRequest(request: HttpServletRequest): HttpServletRequestWrapper(request) {

    private val customHeaders: MutableMap<String, String> = HashMap()

    fun putHeader(name: String, value: String) {
        customHeaders[name] = value
    }

    override fun getHeader(name: String): String {
        val headerValue = customHeaders[name]

        if(headerValue != null)
            return headerValue

        return (request as HttpServletRequest).getHeader(name)
    }

    override fun getHeaderNames(): Enumeration<String>? {
        val set: MutableSet<String> = HashSet(customHeaders.keys)

        val enumeration = (request as HttpServletRequest).headerNames
        while (enumeration.hasMoreElements()) {
            set.add(enumeration.nextElement())
        }

        return Collections.enumeration(set)
    }

    override fun getHeaders(name: String): Enumeration<String> {
        val set: MutableSet<String> = HashSet()
        customHeaders[name]?.let { set.add(it) }

        val enumeration = (request as HttpServletRequest).getHeaders(name)
        while (enumeration.hasMoreElements()) {
            set.add(enumeration.nextElement())
        }

        return Collections.enumeration(set)
    }
}