package com.duda.backbaseassignment.domain.model

import java.math.BigDecimal

data class MovieRating(
    val title: String,
    val boxValue: BigDecimal,
    val averageRating: Short
)