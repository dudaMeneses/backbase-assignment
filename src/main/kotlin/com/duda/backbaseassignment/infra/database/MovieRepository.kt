package com.duda.backbaseassignment.infra.database

import com.duda.backbaseassignment.domain.model.Movie
import com.duda.backbaseassignment.infra.database.records.Tables.MOVIE
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class MovieRepository(private val dslContext: DSLContext) {
    fun upsert(movie: Movie) {
        dslContext.insertInto(MOVIE, MOVIE.TITLE, MOVIE.BOX_VALUE)
            .values(movie.title, movie.boxOfficeValue)
            .onConflict()
            .doUpdate()
            .set(MOVIE.TITLE, movie.title)
            .set(MOVIE.BOX_VALUE, movie.boxOfficeValue)
            .execute()
    }

}
