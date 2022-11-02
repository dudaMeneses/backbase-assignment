package com.duda.backbaseassignment.application.config

import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.Parameter
import org.springdoc.core.customizers.OpenApiCustomiser
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfiguration {
    
    @Bean
    fun customGlobalHeaders(): OperationCustomizer {
        return OperationCustomizer { operation, _ ->
            val tokenParam = Parameter()
                .`in`(ParameterIn.HEADER.toString())
                .schema(StringSchema())
                .name("token")
                .description("Base64 JWT token")
                .required(true)

            operation.addParametersItem(tokenParam)
        }
    }

    @Bean
    fun removeTokenHeaderForGenerateTokenAPI(): OpenApiCustomiser? {
        return OpenApiCustomiser { openApi ->
            openApi.paths.values
                .flatMap { pathItem -> pathItem.readOperations() }
                .forEach { operation: Operation ->
                    if ("getToken" == operation.operationId) {
                        operation.parameters.removeIf { param -> "token" == param.name }
                    }
                }
        }
    }
}