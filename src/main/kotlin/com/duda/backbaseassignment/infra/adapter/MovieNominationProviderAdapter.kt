package com.duda.backbaseassignment.infra.adapter

import com.duda.backbaseassignment.domain.model.MovieNomination
import com.duda.backbaseassignment.domain.model.Nomination
import com.duda.backbaseassignment.domain.model.valueObject.Category
import com.duda.backbaseassignment.domain.port.OscarNominationProvider
import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter
import com.duda.backbaseassignment.infra.database.OscarNominationRepository
import com.duda.backbaseassignment.infra.database.records.tables.records.OscarNominationRecord
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class MovieNominationProviderAdapter(
    private val oscarNominationRepository: OscarNominationRepository,
): OscarNominationProvider {
    @Cacheable("oscarNominations")
    override fun find(filter: OscarNominationFilter): MovieNomination? {
        return oscarNominationRepository.find(filter)?.toDomain()
    }

    private fun OscarNominationRecord.toDomain(): MovieNomination {
        return MovieNomination(
            movieTitle = nominee,
            category = Category.values().first { it.text == category },
            year = year.toInt(),
            won = when(won.toInt()){ 1 -> true else -> false}
        )
    }
}