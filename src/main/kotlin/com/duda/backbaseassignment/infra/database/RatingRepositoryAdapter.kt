package com.duda.backbaseassignment.infra.database

import com.duda.backbaseassignment.domain.model.Rating
import com.duda.backbaseassignment.domain.port.RatingRepository
import com.duda.backbaseassignment.generated.Tables.MOVIE
import com.duda.backbaseassignment.generated.Tables.RATING
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class RatingRepositoryAdapter(private val dslContext: DSLContext): RatingRepository {

    override fun add(rating: Rating) {
        dslContext.insertInto(RATING)
            .set(RATING.RATING_, rating.rate)
            .set(RATING.USER_ID, rating.user)
            .set(RATING.MOVIE_ID, dslContext.select(MOVIE.ID).from(MOVIE).where(MOVIE.TITLE.eq(rating.movie)))
            .execute()
    }

    override fun exists(movieTitle: String, userId: Int): Boolean {
        return dslContext.fetchExists(dslContext.selectOne()
            .from(RATING)
            .join(MOVIE)
            .on(RATING.MOVIE_ID.eq(MOVIE.ID))
            .where(RATING.USER_ID.eq(userId))
            .and(MOVIE.TITLE.eq(movieTitle))
        )
    }
}