package com.duda.backbaseassignment.infra.adapter

import com.duda.backbaseassignment.domain.model.MovieNomination
import com.duda.backbaseassignment.domain.model.valueObject.Category
import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter
import com.duda.backbaseassignment.infra.database.OscarNominationRepository
import com.duda.backbaseassignment.infra.database.records.tables.records.OscarNominationRecord
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.jooq.types.ULong
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class MovieNominationProviderTest {

    @InjectMockKs
    private lateinit var movieNominationProviderAdapter: MovieNominationProviderAdapter

    @MockK
    private lateinit var oscarNominationRepository: OscarNominationRepository

    @BeforeEach
    fun init() = MockKAnnotations.init(this)

    @Test
    fun `when repository returns null then returns null`(){
        every { oscarNominationRepository.find(any()) } returns null

        assertNull(movieNominationProviderAdapter.find(OscarNominationFilter("Test", Category.BEST_PICTURE)))
    }

    @Test
    fun `when movie didn't win oscar then map it to false`(){
        every { oscarNominationRepository.find(any()) } returns OscarNominationRecord(ULong.valueOf(1), 2020, "Test", Category.BEST_PICTURE.text, 0)

        val expectation = MovieNomination("Test", Category.BEST_PICTURE, false, 2020)
        val result = movieNominationProviderAdapter.find(OscarNominationFilter("Test", Category.BEST_PICTURE))

        assertEquals(expectation, result)
    }

    @Test
    fun `when movie won oscar then map it to true`(){
        every { oscarNominationRepository.find(any()) } returns OscarNominationRecord(ULong.valueOf(1), 2020, "Test", Category.BEST_PICTURE.text, 1)

        val expectation = MovieNomination("Test", Category.BEST_PICTURE, true, 2020)
        val result = movieNominationProviderAdapter.find(OscarNominationFilter("Test", Category.BEST_PICTURE))

        assertEquals(expectation, result)
    }

    @Test
    fun `when invalid category from DB then throw exception`(){
        every { oscarNominationRepository.find(any()) } returns OscarNominationRecord(ULong.valueOf(1), 2020, "Test", "Invalid Category", 1)

        assertThrows(IllegalArgumentException::class.java) {movieNominationProviderAdapter.find(OscarNominationFilter("Test", Category.BEST_PICTURE))}
    }
}