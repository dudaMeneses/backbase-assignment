package com.duda.backbaseassignment.domain.service

import com.duda.backbaseassignment.application.rest.model.response.MovieOscarsResponse
import com.duda.backbaseassignment.domain.port.OscarWinnerProvider
import com.duda.backbaseassignment.domain.service.param.OscarWinnerFilter
import org.springframework.stereotype.Service

@Service
class OscarWinnerQuery(
    private val oscarWinnerProvider: OscarWinnerProvider
) {
    fun find(oscarWinnerFilter: OscarWinnerFilter): List<MovieOscarsResponse> {
        TODO("Not yet implemented")
    }

}
