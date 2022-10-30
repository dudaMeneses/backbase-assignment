package com.duda.backbaseassignment.application.config

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class MoneyDeserializerTest {

    private lateinit var deserializer: MoneyDeserializer
    private lateinit var mapper: ObjectMapper

    @BeforeEach
    fun init() {
        deserializer = MoneyDeserializer()
        mapper = ObjectMapper()
    }

    @Test
    fun `when $0 then return 0`(){
        val json = """{"value": "$0"}"""
        Assertions.assertEquals(BigDecimal(0), deserialize(json))
    }

    @Test
    fun `when $316,115,420 then return 316115420`(){
        val json = """{"value": "$316,115,420"}"""
        Assertions.assertEquals(BigDecimal(316115420), deserialize(json))
    }

    @Test
    fun `when empty string then return 0`(){
        val json = """{"value": ""}"""
        Assertions.assertEquals(BigDecimal(0), deserialize(json))
    }

    private fun deserialize(json: String): BigDecimal {
        return mapper.readValue(json, DummyMessage::class.java).value
    }

    data class DummyMessage(
        @JsonDeserialize(using = MoneyDeserializer::class)
        @JsonProperty("value")
        val value: BigDecimal
    )
}