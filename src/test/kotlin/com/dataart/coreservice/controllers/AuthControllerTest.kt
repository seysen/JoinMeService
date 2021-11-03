package com.dataart.coreservice.controllers

import com.dataart.coreservice.AbstractTestClass
import com.dataart.coreservice.dtos.UserDto
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

class AuthControllerTest(
    @Autowired testRest: TestRestTemplate
) : AbstractTestClass(testRest) {

    private val REGISTER_URI = "/register"
    private val LOGIN_URI = "/login"

    private val textValueLength = 6

    @Test
    fun `Verify that POST register works correctly`() {
        val email = RandomStringUtils.randomAlphabetic(textValueLength)
        val password = RandomStringUtils.randomAlphabetic(textValueLength)
        val registerRequest = UserDto(
            RandomStringUtils.randomAlphabetic(textValueLength),
            RandomStringUtils.randomAlphabetic(textValueLength),
            email,
            password
        )
        with(
            testRest
                .postForEntity(REGISTER_URI, registerRequest, HashMap::class.java)
        ) {
            statusCode shouldBe HttpStatus.OK
            body shouldNotBe null
        }
    }

    @Test
    fun `Verify that POST register works incorrectly`() {
        val email = RandomStringUtils.randomAlphabetic(textValueLength)
        val password = RandomStringUtils.randomAlphabetic(textValueLength)
        val registerRequest = UserDto(
            null,
            null,
            email,
            password
        )
        with(
            testRest
                .postForEntity(REGISTER_URI, registerRequest, HashMap::class.java)
        ) {
            statusCode shouldNotBe HttpStatus.OK
            body shouldNotBe null
        }
    }

    @Test
    fun `Verify that UserAlreadyExistException is thrown when the user already exists`() {
        val email = RandomStringUtils.randomAlphabetic(textValueLength)
        val password = RandomStringUtils.randomAlphabetic(textValueLength)
        val registerRequest = UserDto(
            RandomStringUtils.randomAlphabetic(textValueLength),
            RandomStringUtils.randomAlphabetic(textValueLength),
            email,
            password
        )
        testRest.postForEntity(REGISTER_URI, registerRequest, HashMap::class.java)
        with(
            testRest
                .postForEntity(REGISTER_URI, registerRequest, HashMap::class.java)
        ) {
            statusCode shouldBe HttpStatus.CONFLICT
            body shouldNotBe null
        }
    }

    @Test
    fun `Verify that POST login works correctly`() {
        val email = RandomStringUtils.randomAlphabetic(textValueLength)
        val password = RandomStringUtils.randomAlphabetic(textValueLength)
        val registerRequest = UserDto(
            RandomStringUtils.randomAlphabetic(textValueLength),
            RandomStringUtils.randomAlphabetic(textValueLength),
            email,
            password
        )
        val loginRequest = UserDto(
            email = email,
            password = password
        )
        testRest.postForEntity(REGISTER_URI, registerRequest, HashMap::class.java)
        with(
            testRest
                .postForEntity(LOGIN_URI, loginRequest, HashMap::class.java)
        ) {
            statusCode shouldBe HttpStatus.OK
            body shouldNotBe null
        }
    }

    @Test
    fun `Verify that WrongLoginOrPasswordException is thrown when a login request isn't correct`() {
        val loginRequest = UserDto(
            email = RandomStringUtils.randomAlphabetic(textValueLength),
            password = RandomStringUtils.randomAlphabetic(textValueLength)
        )
        with(
            testRest
                .postForEntity(LOGIN_URI, loginRequest, HashMap::class.java)
        ) {
            statusCode shouldBe HttpStatus.UNAUTHORIZED
            body shouldNotBe null
        }
    }
}
