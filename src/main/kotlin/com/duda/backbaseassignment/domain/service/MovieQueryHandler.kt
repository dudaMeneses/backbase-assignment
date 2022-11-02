package com.duda.backbaseassignment.domain.service

import com.duda.backbaseassignment.application.rest.model.response.MovieRatingResponse
import com.duda.backbaseassignment.domain.service.dto.MovieRatingDTO
import com.duda.backbaseassignment.domain.port.MovieRepository
import com.duda.backbaseassignment.domain.service.param.MovieQueryFilter
import org.springframework.stereotype.Service

@Service
class MovieQueryHandler(private val movieRepository: MovieRepository) {
    fun find(filter: MovieQueryFilter): List<MovieRatingResponse> {
        return movieRepository.findOrderedByBoxValue(filter).map { it.toResponse() }
    }

    private fun MovieRatingDTO.toResponse(): MovieRatingResponse = MovieRatingResponse(this.title, this.boxValue, this.averageRating)
}
