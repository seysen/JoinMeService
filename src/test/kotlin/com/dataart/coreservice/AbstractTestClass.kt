package com.dataart.coreservice

import io.kotest.core.spec.style.FreeSpec
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = [AbstractTestClass.Initializer::class])
abstract class AbstractTestClass : FreeSpec() {

    companion object {
        @Container
        val container = PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:13-alpine"))
            .apply {
                withDatabaseName("core_service")
                withUsername("postgres")
                withPassword("12345678")
                withInitScript("db/migration/V1__init.sql")
            }

        var requestSpecification: RequestSpecification = RequestSpecBuilder().setContentType(ContentType.JSON)
            .build() // здесь указать порт не получается тк в этот момент он равен нулю и приобретает значение в тесте
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            container.start()

            TestPropertyValues.of(
                "spring.datasource.url=${container.jdbcUrl}",
                "spring.datasource.password=${container.password}",
                "spring.datasource.username=${container.username}",
            ).applyTo(configurableApplicationContext.environment)
        }
    }

    @LocalServerPort
    var port = 0


}
