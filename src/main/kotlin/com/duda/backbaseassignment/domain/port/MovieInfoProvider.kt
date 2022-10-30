package com.duda.backbaseassignment.domain.port

import com.duda.backbaseassignment.domain.model.Movie
import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter

interface MovieInfoProvider {
    fun getMovieInfo(title: String): Movie?
}
