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

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
    id("io.gitlab.arturbosch.detekt")
    kotlin("kapt")
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

    // detekt
    detekt("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
    detekt("io.gitlab.arturbosch.detekt:detekt-cli:$detektVersion")

    // Testcontainers
    testImplementation("org.testcontainers:postgresql:1.16.0")
    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")

    // testing
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    implementation("io.kotest:kotest-framework-engine:$kotestVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("com.ninja-squad:springmockk:$springmockkVersion")

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
