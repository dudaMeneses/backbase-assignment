package com.duda.backbaseassignment.domain.service.param

data class MovieRatingParam(
    val userId: Long,
    val movieTitle: String,
    val rating: Short
)
