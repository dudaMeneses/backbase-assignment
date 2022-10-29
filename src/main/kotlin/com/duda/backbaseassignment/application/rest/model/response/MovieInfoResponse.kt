package com.duda.backbaseassignment.application.rest.model.response

import java.math.BigDecimal

data class MovieInfoResponse(
    val title: String,
    val boxOfficeValue: BigDecimal
)
