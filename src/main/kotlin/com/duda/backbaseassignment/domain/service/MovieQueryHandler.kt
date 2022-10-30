package com.duda.backbaseassignment.domain.service

import com.duda.backbaseassignment.application.rest.model.response.MovieInfoResponse
import com.duda.backbaseassignment.domain.service.param.MovieQueryFilter
import org.springframework.stereotype.Service

@Service
class MovieQueryHandler {
    fun find(filter: MovieQueryFilter): MovieInfoResponse {
        TODO("to be implemented")
    }

}
