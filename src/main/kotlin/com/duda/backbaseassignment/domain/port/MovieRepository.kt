package com.duda.backbaseassignment.domain.port

import com.duda.backbaseassignment.domain.model.Movie
import com.duda.backbaseassignment.domain.service.dto.MovieRatingDTO
import com.duda.backbaseassignment.domain.service.param.MovieQueryFilter

interface MovieRepository {
    fun upsert(movie: Movie)
    fun findOrderedByBoxValue(filter: MovieQueryFilter): List<MovieRatingDTO>
}