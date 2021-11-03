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

val mapStructVersion: String by project
val mockkVersion: String by project
val springSecurityConfigVersion: String by project
val springStarterConfigVersion: String by project
val jjwtVersion: String by project
val springSecurityJwtVersion: String by project
val assuredTestVersion: String by project
val springmockkVersion: String by project


plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
    id("io.gitlab.arturbosch.detekt")
    kotlin("kapt") version "1.5.31"
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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // SpringBoot
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

    // postgre
    runtimeOnly("org.postgresql:$postgresqlVersion")

    // flyway
    compileOnly("org.flywaydb:$flywaydbVersion")

    detekt("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
    detekt("io.gitlab.arturbosch.detekt:detekt-cli:$detektVersion")

    // Testcontainers
    testImplementation("org.testcontainers:postgresql:1.16.0")
    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")

    //Spring Security
    implementation("org.springframework.security:spring-security-config:$springSecurityConfigVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springStarterConfigVersion")
    implementation("io.jsonwebtoken:jjwt:$jjwtVersion")
    implementation("org.springframework.security:spring-security-jwt:$springSecurityJwtVersion")

    //Test
    testImplementation("io.rest-assured:rest-assured:$assuredTestVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("com.ninja-squad:springmockk:$springmockkVersion")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")

    // mapstruct
    implementation("org.mapstruct:mapstruct:$mapStructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapStructVersion")
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
