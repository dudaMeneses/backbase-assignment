package com.duda.backbaseassignment.application.rest

import com.duda.backbaseassignment.application.rest.model.request.MovieRatingRequest
import com.duda.backbaseassignment.application.rest.model.response.MovieInfoResponse
import com.duda.backbaseassignment.application.rest.model.response.MovieOscarsResponse
import com.duda.backbaseassignment.domain.model.valueObject.Category
import com.duda.backbaseassignment.domain.service.MovieQuery
import com.duda.backbaseassignment.domain.service.OscarWinnerQuery
import com.duda.backbaseassignment.domain.service.RateMovieCommand
import com.duda.backbaseassignment.domain.service.param.MovieQueryFilter
import com.duda.backbaseassignment.domain.service.param.MovieRatingParam
import com.duda.backbaseassignment.domain.service.param.OscarWinnerFilter
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/movies"])
class MovieController(
    private val oscarWinnerQuery: OscarWinnerQuery,
    private val movieQuery: MovieQuery,
    private val rateMovieCommand: RateMovieCommand,
) {

    @GetMapping(path = ["/oscars"])
    fun getMovieOscars(
        @RequestParam("movieTitle", required = true) movieTitle: String,
        @RequestParam("category", required = true, defaultValue = "BEST_PICTURE") category: Category
    ): MovieOscarsResponse {
        return oscarWinnerQuery.find(OscarWinnerFilter(movieTitle = movieTitle, category = category))
    }

    @PostMapping(path = ["/ratings"])
    fun rateMovie(@RequestBody movieRating: MovieRatingRequest) {
        rateMovieCommand.handle(MovieRatingParam(rating =  movieRating.rating, movieTitle = movieRating.movieTitle))
    }

    @GetMapping(path = ["/ratings"])
    fun getMovies(
        @RequestParam("page", required = true, defaultValue = "1") page: Int,
        @RequestParam("size", required = true, defaultValue = "10") size: Int
    ): MovieInfoResponse {
        return movieQuery.find(MovieQueryFilter(page = page, size = size))
    }
}