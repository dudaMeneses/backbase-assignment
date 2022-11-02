package com.duda.backbaseassignment.domain.service.dto

import java.math.BigDecimal

data class MovieRatingDTO(
    val title: String,
    val boxValue: BigDecimal,
    val averageRating: Short
)