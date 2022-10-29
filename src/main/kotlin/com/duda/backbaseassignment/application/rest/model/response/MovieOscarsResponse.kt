package com.duda.backbaseassignment.application.rest.model.response

data class MovieOscarsResponse(
    val movieTitle: String,
    val nominations: List<NominationResponse>
)
