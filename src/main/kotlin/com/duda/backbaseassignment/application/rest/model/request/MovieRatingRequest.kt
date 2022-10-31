package com.duda.backbaseassignment.application.rest.model.request

data class MovieRatingRequest(
    val rating: Short,
    val movieTitle: String
) {
    init {
        assert(rating in 0..5) { "Allowed rating range is from 0 to 5" }
    }
}
