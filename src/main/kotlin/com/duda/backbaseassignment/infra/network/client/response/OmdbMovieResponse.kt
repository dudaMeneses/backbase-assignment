package com.duda.backbaseassignment.infra.network.client.response

import com.duda.backbaseassignment.application.config.MoneyDeserializer
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.math.BigDecimal

data class OmdbMovieResponse(
    @JsonProperty("Title") val title: String,
    @JsonProperty("BoxOffice")
    @JsonDeserialize(using = MoneyDeserializer::class) val boxOffice: BigDecimal
)
