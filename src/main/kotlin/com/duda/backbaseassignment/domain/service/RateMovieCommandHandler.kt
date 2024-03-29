package com.duda.backbaseassignment.domain.service

import com.duda.backbaseassignment.domain.model.Rating
import com.duda.backbaseassignment.domain.port.MovieInfoProvider
import com.duda.backbaseassignment.domain.port.MovieRepository
import com.duda.backbaseassignment.domain.port.RatingRepository
import com.duda.backbaseassignment.domain.service.exception.MovieInfoNotFoundException
import com.duda.backbaseassignment.domain.service.exception.UserAlreadyRatedMovieException
import com.duda.backbaseassignment.domain.service.param.MovieRatingParam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RateMovieCommandHandler(
    private val movieRepository: MovieRepository,
    private val ratingRepository: RatingRepository,
    private val movieInfoProvider: MovieInfoProvider,
) {
    @Transactional
    fun handle(param: MovieRatingParam) {
        val movieInfo = movieInfoProvider.getMovieInfo(param.movieTitle) ?: throw MovieInfoNotFoundException(param.movieTitle)

        movieRepository.upsert(movieInfo)

        if (ratingRepository.exists(param.movieTitle, param.userId)) {
            throw UserAlreadyRatedMovieException(param.movieTitle, param.userId)
        } else {
            ratingRepository.add(param.toDomain())
        }
    }

    private fun MovieRatingParam.toDomain(): Rating = Rating(rate =  this.rating, user = this.userId, movie = this.movieTitle)
}
