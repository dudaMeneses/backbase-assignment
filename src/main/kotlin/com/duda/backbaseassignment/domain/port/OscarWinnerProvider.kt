package com.duda.backbaseassignment.domain.port

import com.duda.backbaseassignment.domain.model.Movie
import com.duda.backbaseassignment.domain.service.param.OscarWinnerFilter

interface OscarWinnerProvider {
    fun find(filter: OscarWinnerFilter): Movie?
}


