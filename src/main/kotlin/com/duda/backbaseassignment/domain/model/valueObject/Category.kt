package com.duda.backbaseassignment.domain.model.valueObject

import java.lang.IllegalArgumentException

enum class Category(val text: String) {
    BEST_PICTURE("Best Picture");

    companion object {
        fun valueFromText(text: String): Category {
            return values().firstOrNull { it.text == text } ?: throw IllegalArgumentException("$text is not a valid category")
        }
    }
}
