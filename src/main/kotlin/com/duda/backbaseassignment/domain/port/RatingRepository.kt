package com.duda.backbaseassignment.domain.port

import com.duda.backbaseassignment.domain.model.Rating

interface RatingRepository {
    fun add(rating: Rating)
}