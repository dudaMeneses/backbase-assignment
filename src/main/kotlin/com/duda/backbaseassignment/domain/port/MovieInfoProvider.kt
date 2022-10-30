package com.duda.backbaseassignment.domain.port

import com.duda.backbaseassignment.domain.model.Movie

interface MovieInfoProvider {
    fun getMovieInfo(title: String): Movie?
}
