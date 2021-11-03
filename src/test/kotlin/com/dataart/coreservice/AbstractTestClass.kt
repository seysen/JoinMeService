package com.dataart.coreservice

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [CoreServiceApplication::class]
)
@Testcontainers
abstract class AbstractTestClass {

    companion object {
        @Container
        val container = PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:13-alpine"))
            .apply {
                withDatabaseName("core_service")
                withUsername("postgres")
                withPassword("12345678")
                withInitScript("db/migration/V1__init.sql")
            }

        @JvmStatic
        @DynamicPropertySource
        fun datasourceConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.password", container::getPassword)
            registry.add("spring.datasource.username", container::getUsername)
        }
    }
}
