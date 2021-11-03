package com.dataart.coreservice.services

import com.dataart.coreservice.AbstractTestClass
import com.dataart.coreservice.dtos.UserDto
import com.dataart.coreservice.exception.UserAlreadyExistException
import com.dataart.coreservice.exception.WrongLoginOrPasswordException
import com.dataart.coreservice.model.User
import com.dataart.coreservice.repository.UserRepository
import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import java.util.*

class UserServiceTest(
    @Autowired testRest: TestRestTemplate,
) : AbstractTestClass(testRest) {

    @MockkBean
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userService: UserService

    private var textValueLength = 6

    @Test
    fun `Verify that User is registered`() {
        val email = RandomStringUtils.randomAlphabetic(textValueLength)
        val userId = 32L
        val password = BCryptPasswordEncoder().encode(email)
        val correctRegisterRequest = UserDto(
            "name",
            "surname",
            email,
            email
        )
        val user = User(
            "name",
            "surname",
            email,
            password
        )
        val expectedId = 32L

        every { userRepository.findByEmail(email) } returns Optional.empty()
        every { userRepository.save(user).id } returns userId

        kotlin.runCatching {
            userService.register(correctRegisterRequest)
        }.onSuccess {
            it shouldNotBe null
            it["userid"] shouldBe expectedId
        }
        verify { userRepository.findByEmail(email) }
    }

    @Test
    fun `Verify that user isn't registered and UserAlreadyExistException is thrown`() {
        // create a user
        // didn't register
        val email = RandomStringUtils.randomAlphabetic(textValueLength)
        val passwordToDto = RandomStringUtils.randomAlphabetic(textValueLength)
        val password = BCryptPasswordEncoder().encode("email")
        val failedRegisterRequest = UserDto(
            "name",
            "surname",
            email,
            passwordToDto
        )

        val user = User(
            "name",
            "surname",
            email,
            password
        )

        every { userRepository.findByEmail(email) } returns Optional.of(user)
        kotlin.runCatching {
            userService.register(failedRegisterRequest)
        }.onFailure {
            (it is UserAlreadyExistException).shouldBe(true)
        }
        verify { userRepository.findByEmail(email) }
    }

    @Test
    fun `Verify that user is authorized`() {
        val email = RandomStringUtils.randomAlphabetic(textValueLength)
        val password = BCryptPasswordEncoder().encode("email")
        val correctLoginRequest = UserDto(
            email = email,
            password = password
        )
        val randomName = RandomStringUtils.randomAlphabetic(textValueLength)
        val randomSurname = RandomStringUtils.randomAlphabetic(textValueLength)
        val userFromRepo = User(
            randomName,
            randomSurname,
            email,
            password)

        userFromRepo.id = 44L
        val expectedId = 44L
        every { userRepository.findByEmail(email) } returns Optional.of(userFromRepo)
        kotlin.runCatching {
            userService.login(correctLoginRequest)
        }.onSuccess {
            it shouldNotBe null
            it["userid"] shouldBe expectedId
        }
        verify { userRepository.findByEmail(email) }
    }

    @Test
    fun `Verify that user is not authorized`() {
        val email = RandomStringUtils.randomAlphabetic(textValueLength)
        val password = BCryptPasswordEncoder().encode("email")
        val correctLoginRequest = UserDto(
            email = email,
            password = password
        )
        every { userRepository.findByEmail(email) } returns Optional.empty()
        kotlin.runCatching {
            userService.login(correctLoginRequest)
        }.onFailure {
            (it is WrongLoginOrPasswordException).shouldBe(true)
        }
        verify { userRepository.findByEmail(email) }
    }

//    @Autowired val testRest = TestRestTemplate()
//
//    @MockBean
//    private lateinit var userRepository: UserRepository
//
//    @Autowired
//    private lateinit var userService: UserService
//
//    private val textValueLength = 6;
//
//    @Test
//    fun `Verify that User is registered`() {
//        val email = RandomStringUtils.randomAlphabetic(textValueLength)
//        val password = RandomStringUtils.randomAlphabetic(textValueLength)
//        val name = RandomStringUtils.randomAlphabetic(textValueLength)
//        val surname = RandomStringUtils.randomAlphabetic(textValueLength)
//
//        runCatching {
//            userService.registerUser(RegisterDto(name, surname, email, password))
//        }.isSuccess
//    }
//
//    @Test
//    fun `Verify that UserAlreadyExist is thrown during registration` () {
//        val email = RandomStringUtils.randomAlphabetic(textValueLength)
//
//        val user = User(RandomStringUtils.randomAlphabetic(textValueLength),
//            RandomStringUtils.randomAlphabetic(textValueLength),
//            email,
//            RandomStringUtils.randomAlphabetic(textValueLength), "")
//
//        val newUserRegisterDto = RegisterDto(
//            RandomStringUtils.randomAlphabetic(textValueLength),
//            RandomStringUtils.randomAlphabetic(textValueLength),
//            email,
//            RandomStringUtils.randomAlphabetic(textValueLength))
//
//
//
//        runCatching {
//            every { userRepository.findByEmail(email) } returns Optional.of(user)
//            userService.registerUser(newUserRegisterDto)
//        }.onFailure {
//            (it is UserAlreadyExistException).shouldBe(true)
//        }
//    }
}
