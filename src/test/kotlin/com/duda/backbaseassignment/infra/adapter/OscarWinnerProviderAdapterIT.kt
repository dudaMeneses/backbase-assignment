package com.duda.backbaseassignment.infra.adapter

import com.duda.backbaseassignment.BackbaseAssignmentApplication
import com.duda.backbaseassignment.domain.model.valueObject.Category
import com.duda.backbaseassignment.domain.port.OscarWinnerProvider
import com.duda.backbaseassignment.domain.service.param.OscarWinnerFilter
import com.duda.backbaseassignment.integration.DatabaseTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@DisplayName("Regarding database search for Oscar Winner")
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackbaseAssignmentApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class OscarWinnerProviderAdapterIT: DatabaseTest() {

    @Autowired
    private lateinit var oscarWinnerProvider: OscarWinnerProvider

    @Test
    fun `when movie was an Oscar winner for Best Picture`(){
        val movie = oscarWinnerProvider.find(OscarWinnerFilter("Chicago", Category.BEST_PICTURE))

        assertTrue(movie!!.nominations.any { it.won && it.category == Category.BEST_PICTURE })
    }

    @Test
    fun `when movie was not an Oscar winner for Best Picture`(){
        val movie = oscarWinnerProvider.find(OscarWinnerFilter("Bugsy", Category.BEST_PICTURE))

        assertTrue(movie!!.nominations.none { it.won && it.category == Category.BEST_PICTURE })
    }

    @Test
    fun `when movie it not in Oscar winner nominations`(){
        val movie = oscarWinnerProvider.find(OscarWinnerFilter("Parangarigo Tirrimiruaru", Category.BEST_PICTURE))

        assertNull(movie)
    }

}