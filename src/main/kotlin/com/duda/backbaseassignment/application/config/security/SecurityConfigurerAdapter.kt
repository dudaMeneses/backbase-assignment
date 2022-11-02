package com.duda.backbaseassignment.application.config.security

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfigurerAdapter(private val jwtValidationFilter: JwtValidationFilter) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.addFilterAfter(jwtValidationFilter, BasicAuthenticationFilter::class.java)

        return http.csrf().disable().build()
    }

    @Bean
    fun jwtValidationFilterRegistration(filter: JwtValidationFilter?): FilterRegistrationBean<*>? {
        val registration: FilterRegistrationBean<*> = FilterRegistrationBean<JwtValidationFilter>(filter)
        registration.isEnabled = false
        return registration
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring()
            .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/swagger-ui/**", "/token/**", "/error")
        }
    }

}