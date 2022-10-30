package com.duda.backbaseassignment.infra.network.client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "omdb")
data class OmdbProperties(
    val url: String,
    val token: String
)
