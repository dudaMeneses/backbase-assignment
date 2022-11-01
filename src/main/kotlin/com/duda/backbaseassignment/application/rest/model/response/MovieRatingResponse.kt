package com.duda.backbaseassignment.application.rest.model.response

import java.math.BigDecimal

data class MovieRatingResponse(val movieTitle: String, val boxValue: BigDecimal, val avgRating: Short)
