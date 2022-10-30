package com.duda.backbaseassignment.application.rest.model.response

import java.math.BigDecimal

data class MovieOscarsResponse(
    val movieTitle: String,
    val boxValue: BigDecimal?,
    val nominations: List<NominationResponse>
)
