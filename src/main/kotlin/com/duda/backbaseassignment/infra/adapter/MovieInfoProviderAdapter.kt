package com.duda.backbaseassignment.infra.adapter

import com.duda.backbaseassignment.domain.model.Movie
import com.duda.backbaseassignment.domain.port.MovieInfoProvider
import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter
import com.duda.backbaseassignment.infra.network.client.OmdbClient
import com.duda.backbaseassignment.infra.network.client.response.OmdbMovieResponse
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class MovieInfoProviderAdapter(
    private val omdbClient: OmdbClient,
): MovieInfoProvider {

    @Cacheable("movieInfoCache")
    override fun getMovieInfo(title: String): Movie? {
      return omdbClient.getMovieInformation(title)?.toDomain()
    }

    private fun OmdbMovieResponse.toDomain() = Movie(
        title = this.title,
        boxOfficeValue = this.boxOffice
    )
}