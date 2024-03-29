package com.duda.backbaseassignment.infra.adapter

import com.duda.backbaseassignment.domain.model.MovieNomination
import com.duda.backbaseassignment.domain.model.valueObject.Category
import com.duda.backbaseassignment.domain.port.OscarNominationProvider
import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter
import com.duda.backbaseassignment.generated.tables.records.OscarNominationRecord
import com.duda.backbaseassignment.infra.database.OscarNominationRepository
import org.springframework.stereotype.Component

@Component
class MovieNominationProviderAdapter(
    private val oscarNominationRepository: OscarNominationRepository,
): OscarNominationProvider {
    override fun find(filter: OscarNominationFilter): MovieNomination? {
        return oscarNominationRepository.find(filter)?.toDomain()
    }

    private fun OscarNominationRecord.toDomain(): MovieNomination {
        return MovieNomination(
            movieTitle = nominee,
            category = Category.valueFromText(category),
            year = year.toInt(),
            won = won
        )
    }
}