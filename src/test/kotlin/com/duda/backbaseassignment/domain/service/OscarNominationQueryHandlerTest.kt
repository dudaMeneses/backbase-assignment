package com.duda.backbaseassignment.domain.service

import com.duda.backbaseassignment.application.rest.model.response.NominationResponse
import com.duda.backbaseassignment.domain.model.MovieNomination
import com.duda.backbaseassignment.domain.model.valueObject.Category
import com.duda.backbaseassignment.domain.port.OscarNominationProvider
import com.duda.backbaseassignment.domain.service.exception.OscarNomineeNotFoundException
import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class OscarNominationQueryHandlerTest {

    @InjectMockKs
    private lateinit var query: OscarNominationQueryHandler

    @MockK
    private lateinit var provider: OscarNominationProvider

    @BeforeEach
    fun init() = MockKAnnotations.init(this)

    @Test
    fun `when no Oscar nomination found then throw exception`(){
        every { provider.find(any()) } returns null

        assertThrows(OscarNomineeNotFoundException::class.java) { query.find(OscarNominationFilter("test-not-found", Category.BEST_PICTURE)) }
    }

    @Test
    fun `when Oscar nomination found then return response entity`(){
        every { provider.find(any()) } returns MovieNomination("test-happy-path", Category.BEST_PICTURE, true, 2020)

        val result = query.find(OscarNominationFilter("test-happy-path", Category.BEST_PICTURE))

        assertEquals(NominationResponse(2020, Category.BEST_PICTURE, true), result)
    }
}