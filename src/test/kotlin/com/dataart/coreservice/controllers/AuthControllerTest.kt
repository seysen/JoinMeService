package com.dataart.coreservice.controllers

import com.dataart.coreservice.AbstractTestClass
import com.dataart.coreservice.dto.UserDto
import com.dataart.coreservice.model.User
import com.dataart.coreservice.repository.UserRepository
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

class AuthControllerTest(
    @Autowired val userRepository: UserRepository
) : AbstractTestClass() {

    private val REGISTER_URI = "/register"
    private val LOGIN_URI = "/login"
    private val bCryptPasswordEncoder = BCryptPasswordEncoder()
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
        Given {
            spec(requestSpecification)
            request().body(registerRequest).port(port)
        } When {
            post(REGISTER_URI)
        } Then {
            statusCode(201)
            body(notNullValue())
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
        Given {
            spec(requestSpecification)
            request().body(registerRequest).port(port)
        } When {
            post(REGISTER_URI)
        } Then {
            statusCode(406)
            body(notNullValue())
        }
    }

    @Test
    fun `Verify that UserAlreadyExistException is thrown when the user already exists`() {
        val email = RandomStringUtils.randomAlphabetic(textValueLength)
        val password = RandomStringUtils.randomAlphabetic(textValueLength)
        val user = User(
            RandomStringUtils.randomAlphabetic(textValueLength),
            RandomStringUtils.randomAlphabetic(textValueLength),
            email,
            password
        )
        val registerRequest = UserDto(
            RandomStringUtils.randomAlphabetic(textValueLength),
            RandomStringUtils.randomAlphabetic(textValueLength),
            email,
            password
        )
        userRepository.save(user)

        Given {
            spec(requestSpecification)
            request().body(registerRequest).port(port)
        } When {
            post(REGISTER_URI)
        } Then {
            statusCode(409)
            body(notNullValue())
        }
    }

    @Test
    fun `Verify that POST login works correctly`() {
        val email = RandomStringUtils.randomAlphabetic(textValueLength)
        val stringPass = RandomStringUtils.randomAlphabetic(textValueLength)
        val password = bCryptPasswordEncoder.encode(stringPass)
        val user = User(
            RandomStringUtils.randomAlphabetic(textValueLength),
            RandomStringUtils.randomAlphabetic(textValueLength),
            email,
            password
        )
        userRepository.save(user)
        val loginRequest = UserDto(
            email = email,
            password = stringPass
        )

        Given {
            spec(requestSpecification)
            request().body(loginRequest).port(port)
        } When {
            post(LOGIN_URI)
        } Then {
            statusCode(200)
            body(Matchers.notNullValue())
        }
    }
}
