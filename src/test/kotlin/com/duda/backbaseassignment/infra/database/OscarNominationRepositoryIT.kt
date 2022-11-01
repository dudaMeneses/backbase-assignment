package com.duda.backbaseassignment.infra.database

import com.duda.backbaseassignment.BackbaseAssignmentApplication
import com.duda.backbaseassignment.domain.model.valueObject.Category
import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter
import com.duda.backbaseassignment.infra.database.records.tables.records.OscarNominationRecord
import com.duda.backbaseassignment.integration.DatabaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackbaseAssignmentApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class OscarNominationRepositoryIT: DatabaseTest() {

    @Autowired
    private lateinit var oscarNominationRepository: OscarNominationRepository

    @Test
    fun `when movie was an Oscar winner for Best Picture`(){
        val nomination: OscarNominationRecord? = oscarNominationRepository.find(OscarNominationFilter("Chicago", Category.BEST_PICTURE))

        assertEquals(1, nomination!!.won)
    }

    @Test
    fun `when movie was not an Oscar winner for Best Picture`(){
        val nomination: OscarNominationRecord? = oscarNominationRepository.find(OscarNominationFilter("Bugsy", Category.BEST_PICTURE))

        assertEquals(0, nomination!!.won)
    }

    @Test
    fun `when movie it not in Oscar winner nominations`(){
        val nomination = oscarNominationRepository.find(OscarNominationFilter("Parangarigo Tirrimiruaru", Category.BEST_PICTURE))

        assertNull(nomination)
    }

}