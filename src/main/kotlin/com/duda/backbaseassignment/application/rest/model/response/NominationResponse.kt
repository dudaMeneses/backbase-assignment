package com.duda.backbaseassignment.application.rest.model.response

import com.duda.backbaseassignment.domain.model.valueObject.Category

data class NominationResponse(
    val nominationYear: Int,
    val category: Category,
    val winner: Boolean
)
