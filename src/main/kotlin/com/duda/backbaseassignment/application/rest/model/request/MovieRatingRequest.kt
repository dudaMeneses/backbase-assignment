package com.duda.backbaseassignment.application.rest.model.request

data class MovieRatingRequest(
    val rating: Short,
    val movieTitle: String
)
