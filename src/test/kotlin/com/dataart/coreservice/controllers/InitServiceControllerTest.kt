package com.dataart.coreservice.controllers

import com.dataart.coreservice.AbstractTestClass
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

class InitServiceControllerTest(@Autowired val testRest: TestRestTemplate) : AbstractTestClass() {

    @Test
    fun coreServiceControllerTest() {
        val webTestEntity = testRest.getForEntity<String>("/")
        Assertions.assertThat(webTestEntity.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(webTestEntity.body).contains("Hello world! We are CORE!")
    }
}
