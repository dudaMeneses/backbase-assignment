package com.duda.backbaseassignment.application.rest.model.request

data class MovieRatingRequest(
    val rating: Int,
    val movieTitle: String
)
