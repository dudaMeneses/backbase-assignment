package com.duda.backbaseassignment.domain.model

import com.duda.backbaseassignment.domain.model.valueObject.Category

abstract class Nomination {
    abstract val category: Category
    abstract val won: Boolean
    abstract val year: Int
}

data class MovieNomination(
    val movieTitle: String,
    override val category: Category,
    override val won: Boolean,
    override val year: Int
) : Nomination()
