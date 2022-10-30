package com.duda.backbaseassignment

import com.duda.backbaseassignment.infra.network.client.OmdbProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(OmdbProperties::class)
class BackbaseAssignmentApplication

fun main(args: Array<String>) {
	runApplication<BackbaseAssignmentApplication>(*args)
}
