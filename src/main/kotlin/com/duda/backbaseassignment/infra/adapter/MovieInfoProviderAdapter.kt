package com.duda.backbaseassignment.infra.adapter

import com.duda.backbaseassignment.domain.model.Movie
import com.duda.backbaseassignment.domain.port.MovieInfoProvider
import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter
import com.duda.backbaseassignment.infra.network.client.OmdbClient
import com.duda.backbaseassignment.infra.network.client.response.OmdbMovieResponse
import org.springframework.stereotype.Component

@Component
class MovieInfoProviderAdapter(
    private val omdbClient: OmdbClient,
): MovieInfoProvider {

    fun getMovieInfo(filter: OscarNominationFilter): Movie? {
      return omdbClient.getMovieInformation(filter.nominee)?.toDomain()
    }

    private fun OmdbMovieResponse.toDomain() = Movie(
        title = this.title,
        boxOfficeValue = this.boxOffice
    )
}