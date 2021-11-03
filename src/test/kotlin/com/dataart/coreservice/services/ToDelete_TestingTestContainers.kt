package com.dataart.coreservice.services

import com.dataart.coreservice.AbstractTestClass
import com.dataart.coreservice.model.User
import com.dataart.coreservice.repository.UserRepository
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ToDelete_TestingTestContainers(@Autowired private val userRepository: UserRepository) : AbstractTestClass() {

    @Test
    fun `Verify that testcontainers work`() =
        userRepository.save(
            User(
                "test",
                "test",
                "test",
                "test",
                "test"
            )
        ).also {
            userRepository.findById(it.id) shouldNotBe null
        }
}
