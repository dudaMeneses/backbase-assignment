package com.duda.backbaseassignment.infra.database

import com.duda.backbaseassignment.domain.model.valueObject.Category
import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter
import com.duda.backbaseassignment.generated.Tables
import com.duda.backbaseassignment.generated.tables.records.OscarNominationRecord
import com.duda.backbaseassignment.integration.IntegrationTest
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
internal class OscarNominationRepositoryIT: IntegrationTest() {

    @Autowired
    private lateinit var oscarNominationRepository: OscarNominationRepository

    @Autowired
    private lateinit var dslContext: DSLContext

    @Test
    fun `when movie was an Oscar winner for Best Picture`(){
        insertOscarNomination("Chicago", true)

        val nomination: OscarNominationRecord? = oscarNominationRepository.find(OscarNominationFilter("Chicago", Category.BEST_PICTURE))

        assertTrue(nomination!!.won)
    }

    @Test
    fun `when movie was not an Oscar winner for Best Picture`(){
        insertOscarNomination("Bugsy", false)

        val nomination: OscarNominationRecord? = oscarNominationRepository.find(OscarNominationFilter("Bugsy", Category.BEST_PICTURE))

        assertFalse(nomination!!.won)
    }

    @Test
    fun `when movie it not in Oscar winner nominations`(){
        val nomination = oscarNominationRepository.find(OscarNominationFilter("Parangarigo Tirrimiruaru", Category.BEST_PICTURE))

        assertNull(nomination)
    }

    fun insertOscarNomination(movieTitle: String, won: Boolean){
            dslContext.insertInto(Tables.OSCAR_NOMINATION)
                .set(Tables.OSCAR_NOMINATION.NOMINEE, movieTitle)
                .set(Tables.OSCAR_NOMINATION.WON, won)
                .set(Tables.OSCAR_NOMINATION.YEAR, 2010)
                .set(Tables.OSCAR_NOMINATION.CATEGORY, Category.BEST_PICTURE.text)
                .execute()
    }

}