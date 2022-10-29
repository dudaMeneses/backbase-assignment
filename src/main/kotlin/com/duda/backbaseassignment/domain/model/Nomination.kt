package com.duda.backbaseassignment.domain.model

import com.duda.backbaseassignment.domain.model.valueObject.Category

data class Nomination(val category: Category, val won: Boolean, val year: Int)
