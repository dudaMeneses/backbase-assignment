package com.duda.backbaseassignment.domain.port

import com.duda.backbaseassignment.domain.model.Movie

interface MovieRepository {
    fun upsert(movie: Movie)
}