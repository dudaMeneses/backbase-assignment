package com.duda.backbaseassignment.domain.service.param

data class MovieQueryFilter(val pageIndex: Int, val size: Int) {
    init {
        assert(size <= 50) { "Page size can't contain more than 50 movies" }
    }
}
