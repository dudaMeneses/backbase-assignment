package com.duda.backbaseassignment.domain.service

import com.duda.backbaseassignment.application.rest.model.response.NominationResponse
import com.duda.backbaseassignment.domain.model.Nomination
import com.duda.backbaseassignment.domain.port.OscarNominationProvider
import com.duda.backbaseassignment.domain.service.exception.OscarNomineeNotFoundException
import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter
import org.springframework.stereotype.Service

@Service
class OscarWinnerQuery(
    private val oscarNominationProvider: OscarNominationProvider
) {
    fun find(filter: OscarNominationFilter): NominationResponse {
        return oscarNominationProvider.find(filter)?.toResponse() ?: throw OscarNomineeNotFoundException(filter.nominee)
    }

    private fun Nomination.toResponse(): NominationResponse {
        return NominationResponse(nominationYear = this.year, winner = this.won, category = this.category)
    }

}
