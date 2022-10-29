package com.duda.backbaseassignment.infra.adapter

import com.duda.backbaseassignment.domain.model.Movie
import com.duda.backbaseassignment.domain.model.Nomination
import com.duda.backbaseassignment.domain.model.valueObject.Category
import com.duda.backbaseassignment.domain.port.OscarWinnerProvider
import com.duda.backbaseassignment.domain.service.param.OscarWinnerFilter
import com.duda.backbaseassignment.infra.database.records.Tables.MOVIE
import com.duda.backbaseassignment.infra.database.records.Tables.OSCAR_NOMINATION
import com.duda.backbaseassignment.infra.database.records.tables.OscarNomination
import com.duda.backbaseassignment.infra.database.records.tables.records.OscarNominationRecord
import org.jooq.DSLContext
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class OscarWinnerProviderAdapter(
    private val dslContext: DSLContext
): OscarWinnerProvider {
    @Cacheable("findOscarWinner")
    override fun find(filter: OscarWinnerFilter): Movie? {
        return dslContext.select()
            .from(OSCAR_NOMINATION)
            .where(OSCAR_NOMINATION.NOMINEE.eq(filter.movieTitle)
                .and(OSCAR_NOMINATION.CATEGORY.eq(filter.category.text)))
            .fetchInto(OscarNominationRecord::class.java)
            .map { it.toDomain() }
            .firstOrNull()
    }

    private fun OscarNominationRecord.toDomain(): Movie {
        return Movie(
            title = nominee,
            nominations = listOf(Nomination(
                category = Category.values().first { it.text == category },
                year = year.toInt(),
                won = when(won.toInt()){ 1 -> true else -> false}
            ))
        )
    }
}