package com.duda.backbaseassignment.infra.database

import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter
import com.duda.backbaseassignment.generated.Tables
import com.duda.backbaseassignment.generated.tables.records.OscarNominationRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class OscarNominationRepository(private val dslContext: DSLContext) {
    fun find(filter: OscarNominationFilter): OscarNominationRecord? {
        return dslContext.select()
            .from(Tables.OSCAR_NOMINATION)
            .where(
                Tables.OSCAR_NOMINATION.NOMINEE.eq(filter.nominee)
                .and(Tables.OSCAR_NOMINATION.CATEGORY.eq(filter.category.text)))
            .fetchInto(OscarNominationRecord::class.java)
            .firstOrNull()
    }

}
