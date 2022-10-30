package com.duda.backbaseassignment.domain.port

import com.duda.backbaseassignment.domain.model.Nomination
import com.duda.backbaseassignment.domain.service.param.OscarNominationFilter

interface OscarNominationProvider {
    fun find(filter: OscarNominationFilter): Nomination?
}


