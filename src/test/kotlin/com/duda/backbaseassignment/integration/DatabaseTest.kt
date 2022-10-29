package com.duda.backbaseassignment.integration

import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@ActiveProfiles(value = ["integration-test"])
@Testcontainers(disabledWithoutDocker = true)
open class DatabaseTest {

    companion object {
        private val container = MySQLContainer("mysql:8.0").apply {
            withDatabaseName("movies_info")
            withCommand("--local-infile=ON --character-set-server=latin1 --collation-server=latin1_swedish_ci")
            start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { "${container.jdbcUrl}?allowLoadLocalInfile=true" }
            registry.add("spring.datasource.username", container::getUsername)
            registry.add("spring.datasource.password", container::getPassword)
        }
    }

}
