package com.duda.backbaseassignment.domain.service

import com.duda.backbaseassignment.application.rest.model.response.MovieOscarsResponse
import com.duda.backbaseassignment.application.rest.model.response.NominationResponse
import com.duda.backbaseassignment.domain.model.Movie
import com.duda.backbaseassignment.domain.port.OscarWinnerProvider
import com.duda.backbaseassignment.domain.service.exception.OscarNomineeNotFoundException
import com.duda.backbaseassignment.domain.service.param.OscarWinnerFilter
import org.springframework.stereotype.Service

@Service
class OscarWinnerQuery(
    private val oscarWinnerProvider: OscarWinnerProvider
) {
    fun find(filter: OscarWinnerFilter): MovieOscarsResponse {
        return oscarWinnerProvider.find(filter)?.toResponse() ?: throw OscarNomineeNotFoundException(filter.movieTitle)
    }

    private fun Movie.toResponse(): MovieOscarsResponse {
        return MovieOscarsResponse(
            movieTitle = this.title,
            nominations = this.nominations.map {
                NominationResponse(nominationYear = it.year, winner = it.won, category = it.category)
            }
        )
    }

}
