package com.duda.backbaseassignment.domain.model

import java.math.BigDecimal

data class Movie(
    val title: String,
    val nominations: List<Nomination>,
    val boxValue: BigDecimal? = null
)
