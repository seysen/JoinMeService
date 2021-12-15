import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val projectVersion: String by project
val groupName: String by project
val targetJvm: String by project
val springBootVersion: String by project
val kotlinVersion: String by project
val jacksonVersion: String by project
val postgresqlVersion: String by project
val flywaydbVersion: String by project
val detektVersion: String by project
val kotestVersion: String by project
val testcontainersVersion: String by project
val mapstruct: String by project
val mapstructProcessor: String by project
val slf4jVersion: String by project
val logbackVersion: String by project
val apacheCommonLangVersion: String by project
val mockkVersion: String by project
val springmockkVersion: String by project
val springCloudSleuth: String by project
val kaptVersion: String by project
val mapStructVersion: String by project
val restAssuredVersion: String by project
val kotlinxCoroutinesTest: String by project
val kotestExtensionsSpring: String by project
val kotestExtensionsTestcontainersJvm: String by project
val springSecurityConfigVersion: String by project
val springStarterConfigVersion: String by project
val jjwtVersion: String by project
val springSecurityJwtVersion: String by project
val awsVersion: String by project
val postgresqlTestContainersVersion: String by project

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
    id("io.gitlab.arturbosch.detekt")
    kotlin("kapt")
    id("org.flywaydb.flyway")
}

group = groupName
version = projectVersion
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // SpringBoot
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

    // postgre
    runtimeOnly("org.postgresql:$postgresqlVersion")

    // flyway
    implementation("org.flywaydb:flyway-core:$flywaydbVersion")

    // detekt
    detekt("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
    detekt("io.gitlab.arturbosch.detekt:detekt-cli:$detektVersion")

    // Testcontainers
    testImplementation("org.testcontainers:postgresql:$postgresqlTestContainersVersion")
    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")

    // testing
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-engine:$kotestVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("com.ninja-squad:springmockk:$springmockkVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesTest") // для Free Spec
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion") // не видит тесты free spec без этой зависимости
    testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestExtensionsSpring") //  для DI

    // mapstruct
    implementation("org.mapstruct:mapstruct:$mapStructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapStructVersion")

    // logging
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("ch.qos.logback:logback-core:$logbackVersion")
    testImplementation("ch.qos.logback:logback-classic:$logbackVersion")

    // utils
    implementation("org.apache.commons:commons-lang3:$apacheCommonLangVersion")

    // tracing
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth:$springCloudSleuth")

    //Spring Security
    implementation("org.springframework.security:spring-security-config:$springSecurityConfigVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springStarterConfigVersion")
    implementation("io.jsonwebtoken:jjwt:$jjwtVersion")
    implementation("org.springframework.security:spring-security-jwt:$springSecurityJwtVersion")

    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.amazonaws:aws-java-sdk-s3:$awsVersion")
}

apply(from = "detekt.gradle")

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = targetJvm
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
}

flyway {
    url = "jdbc:postgresql://localhost:5432/core_service"
    user = "postgres"
    password = "12345678"
}
