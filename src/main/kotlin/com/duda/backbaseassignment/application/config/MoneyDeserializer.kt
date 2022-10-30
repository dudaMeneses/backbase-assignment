package com.duda.backbaseassignment.application.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class MoneyDeserializer: JsonDeserializer<BigDecimal>() {
    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): BigDecimal {
        val text = parser?.text

        if (text == null || text == "")
            return BigDecimal.ZERO

        return BigDecimal(NumberFormat.getCurrencyInstance(Locale.US).parse(text).toDouble())
    }

}
