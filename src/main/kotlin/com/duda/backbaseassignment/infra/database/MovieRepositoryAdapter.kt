package com.duda.backbaseassignment.infra.database

import com.duda.backbaseassignment.domain.model.Movie
import com.duda.backbaseassignment.domain.model.MovieRating
import com.duda.backbaseassignment.domain.port.MovieRepository
import com.duda.backbaseassignment.domain.service.param.MovieQueryFilter
import com.duda.backbaseassignment.infra.database.records.Tables.MOVIE
import com.duda.backbaseassignment.infra.database.records.Tables.RATING
import org.jooq.DSLContext
import org.jooq.impl.DSL.avg
import org.springframework.stereotype.Repository
import java.math.MathContext
import kotlin.math.roundToInt

@Repository
class MovieRepositoryAdapter(private val dslContext: DSLContext): MovieRepository {
    override fun upsert(movie: Movie) {
        dslContext.insertInto(MOVIE, MOVIE.TITLE, MOVIE.BOX_VALUE)
            .values(movie.title, movie.boxOfficeValue)
            .onConflict()
            .doUpdate()
            .set(MOVIE.TITLE, movie.title)
            .set(MOVIE.BOX_VALUE, movie.boxOfficeValue)
            .execute()
    }

    override fun findOrderedByBoxValue(filter: MovieQueryFilter): List<MovieRating> {
        return dslContext.select(MOVIE.TITLE, MOVIE.BOX_VALUE, avg(RATING.RATING_))
            .from(MOVIE)
            .join(RATING).on(MOVIE.ID.eq(RATING.MOVIE_ID))
            .groupBy(MOVIE.ID)
            .orderBy(avg(RATING.RATING_).desc(), MOVIE.BOX_VALUE.desc())
            .limit(filter.size)
            .offset(filter.pageIndex * filter.size)
            .fetch { (title, boxValue, avgRating) -> MovieRating(title, boxValue, avgRating.toDouble().roundToInt().toShort()) }
    }

}
