package com.duda.backbaseassignment.infra.database.config

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.RenderNameCase
import org.jooq.conf.RenderQuotedNames
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DataSourceConnectionProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import javax.sql.DataSource

@Configuration
class JooqConfig {

    @Bean
    fun dslContext(dataSource: DataSource): DSLContext {
        val settings = Settings()
            .withRenderQuotedNames(RenderQuotedNames.NEVER)
            .withRenderNameCase(RenderNameCase.LOWER)

        return DSL.using(DataSourceConnectionProvider(TransactionAwareDataSourceProxy(dataSource)), SQLDialect.MYSQL, settings)
    }
}