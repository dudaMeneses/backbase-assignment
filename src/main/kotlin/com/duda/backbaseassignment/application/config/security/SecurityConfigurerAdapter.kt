package com.duda.backbaseassignment.application.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
class SecurityConfigurerAdapter(private val jwtValidationFilter: JwtValidationFilter) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.addFilterAfter(jwtValidationFilter, BasicAuthenticationFilter::class.java)

        return http.csrf().disable().build()
    }
}