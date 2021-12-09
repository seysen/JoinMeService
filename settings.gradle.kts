pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val springJpaVersion: String by settings
    val allOpenVersion: String by settings
    val detektVersion: String by settings
    val kaptVersion: String by settings
    val flywaydbVersion: String by settings

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version springJpaVersion
        kotlin("plugin.allopen") version allOpenVersion
        id("io.gitlab.arturbosch.detekt") version detektVersion
        kotlin("kapt") version kaptVersion
        id("org.flywaydb.flyway") version flywaydbVersion
     }
}

rootProject.name = "CoreService"
